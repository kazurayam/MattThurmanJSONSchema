package com.ws

import org.everit.json.schema.Schema
import org.everit.json.schema.loader.SchemaLoader
import org.everit.json.schema.ValidationException
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.annotation.Keyword

class EveritValidator {

	/*** Send request and verify status code
	 * * @param stringJson Json to validate
	 * * @param stringSchema validation schema
	 * */

	@Keyword
	def verifyJsonSchema(String stringJson, String stringSchema) {
		JSONObject rawSchema = new JSONObject(new JSONTokener(stringSchema))
		Schema schema = SchemaLoader.load(rawSchema)
		try {

			//schema.validate(new JSONObject(stringJson))
			schema.validate(new JSONArray(stringJson))

			KeywordUtil.markPassed("Schema is valid - PASSED")
		} catch (ValidationException e) {
			StringBuffer outmessage = new StringBuffer()
			outmessage << e.getMessage() << "\n"
			e.getAllMessages().each { msg -> outmessage << "$msg \n" }
			KeywordUtil.markFailed(outmessage as String)
		}
	}
}