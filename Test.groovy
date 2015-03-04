@Grab('org.spockframework:spock-core:0.7-groovy-2.0')
@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7.1')

import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.*
import org.apache.http.client.HttpResponseException
import spock.lang.Specification
import spock.lang.Unroll

class Test extends Specification {

	def ROOT = "http://localhost:4567/"
	def http = new RESTClient(ROOT)

	def "URL NotFound"() {
		when:
		http.get(path: "hoge")

		then:
		def e = thrown(HttpResponseException)
		e.statusCode == 404
		e.message == "Not Found"
	}

	@Unroll
	def "test bookmarks"() {
		when:
		def res = http.get(path: "bookmarks", contentType: JSON)
		def resData = res.responseData

		then:
		notThrown(HttpResponseException)
		resData.size() == 3
		resData[i].id == t_id
		resData[i].name == t_name
		resData[i].url == t_url

		where:
		i || t_id | t_name    | t_url
		0 || "1"  | "オキニ1" | "http:/hogehoge.com/okini1"
		1 || "2"  | "オキニ2" | "http:/hogehoge.com/okini2"
		2 || "3"  | "オキニ3" | "http:/hogehoge.com/okini3"
	}

	@Unroll
	def "test bookmarks/#t_id"() {
		when:
		def res = http.get(path: "bookmarks/${t_id}", contentType: JSON)
		def resData = res.responseData

		then:
		notThrown(HttpResponseException)
		resData.size() == 3
		resData.id == t_id
		resData.name == t_name
		resData.url == t_url

		where:
		t_id | t_name    | t_url
		"1"  | "オキニ1" | "http:/hogehoge.com/okini1"
		"2"  | "オキニ2" | "http:/hogehoge.com/okini3"
		"3"  | "オキニ3" | "http:/hogehoge.com/okini3"
	}

}