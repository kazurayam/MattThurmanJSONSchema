package com.kazurayam.jsonschema4ks

import org.everit.json.schema.Schema
import org.everit.json.schema.loader.SchemaLoader
import org.everit.json.schema.ValidationException
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.annotation.Keyword

class EveritValidator {

	/*** Send request and verify status code
	 * * @param stringJson Json to validate
	 * * @param stringSchema validation schema
	 * */

	@Keyword
	boolean validate(String stringJson, String stringSchema) {
		JSONObject rawSchema = new JSONObject(new JSONTokener(stringSchema))
		Schema schema = SchemaLoader.load(rawSchema)
		Object json
		try {
			JSONParser parser = new JSONParser()
			json = parser.parse(stringJson)
			println "validate step1"
			try {
				println "validate step2"
				schema.validate(json)
				println "validate step3"
				KeywordUtil.markPassed("JSON instance is valid against Schema - PASSED")
				return true
			} catch (ValidationException ve) {
				println "validate step4"
				StringBuffer outmessage = new StringBuffer()
				outmessage << ve.getMessage() << "\n"
				ve.getAllMessages().each { msg -> outmessage << "$msg \n" }
				KeywordUtil.markFailed(outmessage as String)
				return false
			}
		} catch (ParseException pe) {
			println "validate step5"
			StringBuffer outmessage = new StringBuffer()
			outmessage << pe.getMessage() << "\n"
			pe.getAllMessages().each { msg -> outmessage << "$msg \n" }
			KeywordUtil.markFailed(outmessage as String)
			return false
		}
	}
}