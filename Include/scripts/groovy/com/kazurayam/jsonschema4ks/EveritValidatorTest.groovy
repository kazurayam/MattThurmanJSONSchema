package com.kazurayam.jsonschema4ks

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
class EveritValidatorTest {

	private static Path instanceOfJsonArray
	private static Path schemaForJsonArray
	private static Path instanceOfJsonObject
	private static Path schemaForJsonObject

	@BeforeClass
	public static void onlyOnce() {
		Path outDir = Paths.get('tmp')
		Files.createDirectories(outDir)
		// example from https://json-schema.org/understanding-json-schema/reference/array.html
		instanceOfJsonArray = this.createFile(outDir, 'instanceOfJsonArray.json', '''
[3, "different", { "types" : "of values" }]
''')
		schemaForJsonArray = this.createFile(outDir, 'schemaForJsonArray.json', '''
{ "type": "array" }
''')
		// example from https://json-schema.org/understanding-json-schema/reference/object.html
		instanceOfJsonObject = this.createFile(outDir, 'instanceOfJsonObject.json', '''
{
    "Sun"     : 1.9891e30,
    "Jupiter" : 1.8986e27,
    "Saturn"  : 5.6846e26,
    "Neptune" : 10.243e25,
    "Uranus"  : 8.6810e25,
    "Earth"   : 5.9736e24,
    "Venus"   : 4.8685e24,
    "Mars"    : 6.4185e23,
    "Mercury" : 3.3022e23,
    "Moon"    : 7.349e22,
    "Pluto"   : 1.25e22
}
''')
		schemaForJsonObject = this.createFile(outDir, 'schemaForJsonObject.json', '''
{ "type": "object" }
''')
	}

	@Test
	void test_validateJsonArrayAgainstSchema() {
		String json   = instanceOfJsonArray.toFile().text
		String schema = schemaForJsonArray.toFile().text
		boolean result = new EveritValidator().validate(json, schema)
		assertTrue("Validation failed. instance=\'${json}\', schema=\'${schema}\'", result)
	}

	@Test
	void test_validateJsonObjectAgainstSchema() {
		String json   = instanceOfJsonObject.toFile().text
		String schema = schemaForJsonObject.toFile().text
		boolean result = new EveritValidator().validate(json, schema)
		assertTrue("Validation failed. instance=\'${json}\', schema=\'${schema}\'", result)
	}

	private static Path createFile(Path outDir, String fileName, String content) {
		Path p = outDir.resolve(fileName)
		if (Files.exists(p)) {
			Files.delete(p)
		}
	    Files.createFile(p)
		p.toFile().text = content
		return p
	}
}
