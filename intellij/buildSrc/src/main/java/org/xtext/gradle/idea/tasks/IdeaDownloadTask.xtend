package org.xtext.gradle.idea.tasks

import groovy.lang.Closure
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.util.regex.Pattern
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Data
import org.gradle.api.DefaultTask
import org.gradle.api.file.CopySpec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.os.OperatingSystem

import static org.xtext.gradle.idea.tasks.IdeaDownloadTask.*

@Accessors
class IdeaDownloadTask extends DefaultTask {
	static val os = OperatingSystem.current

	Object ideaHome
	@Input String ideaVersion

	new() {
		onlyIf[ideaHomeDir.list?.size < 3]
	}

	@TaskAction
	def download() {
		val buildInfo = queryBuildInfo
		val archiveFile = new File(ideaHomeDir, buildInfo.archiveName)
		if (!archiveFile.exists) {
			Files.copy(new URL(buildInfo.archiveUrl).openStream, archiveFile.toPath)
		}
		project.copy(new Closure(null) {
			
			override getMaximumNumberOfParameters() {
				1
			}
			
			override call(Object arguments) {
				arguments as CopySpec => [
					into(ideaHomeDir)
					if (os.isLinux) {
						from(project.tarTree(archiveFile))
						eachFile[path = path.cutDirs(1)]
					} else {
						from(project.zipTree(archiveFile))
						if (os.isMacOsX) {
							eachFile[path = path.cutDirs(2)]
						}
					}
				]
			}

		})
	}

	@OutputDirectory
	def getIdeaHomeDir() {
		project.file(ideaHome)
	}

	def queryBuildInfo() {
		val buildApiUrl = "https://teamcity.jetbrains.com/guestAuth/app/rest/builds"

		val buildLocator = '''buildType:bt410,status:SUCCESS,branch:idea/«ideaVersion»'''
		val buildIdRequestUrl = '''«buildApiUrl»/?locator=«buildLocator»'''
		val buildId = httpGet(buildIdRequestUrl) [ response |
			val pattern = Pattern.compile('^(.*)\\sid="(\\d+)"(.*)$')
			val matcher = pattern.matcher(response)
			matcher.find
			matcher.group(2)
		]

		val buildUrl = '''«buildApiUrl»/id:«buildId»'''

		val buildNumberRequestUrl = '''«buildUrl»/artifacts/children'''
		val buildNumber = httpGet(buildNumberRequestUrl) [ response |
			val pattern = Pattern.compile('^(.*)ideaIC-([\\w\\.]+)\\.win\\.zip(.*)$')
			val matcher = pattern.matcher(response)
			matcher.find
			matcher.group(2)
		]
		val archiveName = '''ideaIC-«buildNumber».«os.archiveExtension»'''

		val contentBaseUrl = '''«buildUrl»/artifacts/content'''
		val archiveUrl = '''«contentBaseUrl»/«archiveName»'''

		new IdeaBuildInfo(ideaVersion, buildId, buildUrl, buildNumber, archiveName, archiveUrl)
	}

	def httpGet(String url, (String)=>String responseHandler) {
		val client = new DefaultHttpClient()
		val request = new HttpGet(url)
		val result = try {
			val response = EntityUtils.toString(client.execute(request).entity)
			responseHandler.apply(response)
		} finally {
			request.releaseConnection
		}
		result
	}

	def archiveExtension(OperatingSystem os) {
		if (os.isWindows) {
			"win.zip"
		} else if (os.isMacOsX) {
			"mac.zip"
		} else {
			"tar.gz"
		}
	}

	def cutDirs(String file, int levels) {
		val canonicalPath = path.replaceAll("\\\\", "/")
		canonicalPath.split('/').drop(levels).join("/")
	}
}

@Data class IdeaBuildInfo {
	String version
	String buildId
	String buildUrl
	String buildNumber
	String archiveName
	String archiveUrl
}