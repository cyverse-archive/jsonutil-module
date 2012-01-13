package org.iplantc.core.jsonutil;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.junit.client.GWTTestCase;

public class GwtTestJsonUtil extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "org.iplantc.core.JsonUtil";
    }

    public void testTrimNull() {
        assertNull(JsonUtil.trim(null));
    }

    public void testTrimEmpty() {
        String empty = JsonUtil.trim("");
        assertNotNull(empty);
        assertTrue("".equals(empty));
    }

    public void testTrimNoQuotes() {
        String tmp = JsonUtil.trim("test");
        assertNotNull(tmp);
        assertTrue("test".equals(tmp));

    }

    public void testTrimLeftUnbalanced() {
        String tmp = JsonUtil.trim("\"test");
        assertNotNull(tmp);
        assertTrue("test".equals(tmp));
    }

    public void testTrimRightUnbalanced() {
        String tmp = JsonUtil.trim("test\"");
        assertNotNull(tmp);
        assertTrue("test".equals(tmp));
    }

    public void testTrimQuoteinMiddle() {
        String str = "\"te\"st\"";
        String tmp = JsonUtil.trim(str);
        assertNotNull(tmp);
        assertTrue("te\"st".equals(tmp));

    }

    public void testTrim() {
        String tmp = JsonUtil.trim("\"test\"");
        assertNotNull(tmp);
        assertTrue("test".equals(tmp));
    }

    public void testEscapeNewLine() {
        String tmp = "this is a string with newline\n";
        assertEquals(JsonUtil.escapeNewLine(tmp), "this is a string with newline\\n");
    }

    public void testEscapeNewLineNull() {
        String tmp = null;
        assertNull(JsonUtil.escapeNewLine(tmp));
    }

    public void testEscapeNewLineEmpty() {
        String tmp = "";
        assertEquals("", JsonUtil.escapeNewLine(tmp));

    }

    public void testFormatString() {
        String tmp = "this is a string with newline\\n";
        assertEquals(JsonUtil.formatString(tmp), "this is a string with newline\n");
    }

    public void testtestFormatStringNull() {
        String tmp = null;
        assertNull(JsonUtil.formatString(tmp));
    }

    public void testFormatStringEmpty() {
        String tmp = "";
        assertEquals("", JsonUtil.formatString(tmp));

    }

    public void testNullIsEmpty() {
        assertEquals(true, JsonUtil.isEmpty(null));
    }

    public void testEmptyIsEmpty1() {
        String tmp = "{}";
        JSONValue val = JSONParser.parseStrict(tmp);
        assertEquals(true, JsonUtil.isEmpty(val));
    }

    public void testEmptyIsEmpty2() {
        String tmp = "[]";
        JSONValue val = JSONParser.parseStrict(tmp);
        assertEquals(true, JsonUtil.isEmpty(val));
    }

    public void testNonEmptyIsEmpty() {
        String tmp = "{\"testkey\": \"testvalue\"}";
        JSONValue val = JSONParser.parseStrict(tmp);
        assertEquals(false, JsonUtil.isEmpty(val));
    }

    public void testGetRawValueAsString() {
        JSONNumber number = new JSONNumber(12345);
        assertEquals("12345", JsonUtil.getRawValueAsString(number));

        JSONString string = new JSONString("test 123 test");
        assertEquals("test 123 test", JsonUtil.getRawValueAsString(string));

        JSONBoolean bool = JSONBoolean.getInstance(true);
        assertEquals("true", JsonUtil.getRawValueAsString(bool));
    }

    public void testBuildStringArray() {
        // test buildStringArray(String, List)
        assertEquals("\"test 123\": []", JsonUtil.buildStringArray("test 123", null));
        String exp = "\"blah\": [\"asdf\",\"2324523\",\"lkjlkjlkj\"]";
        String act =  JsonUtil.buildStringArray("blah", Arrays.asList("asdf", "2324523", "lkjlkjlkj"));
        System.out.println("|" + exp + "|");
        System.out.println("|" + act + "|");
        assertEquals(exp, act);

        // test buildStringArray(List)
        assertTrue(JsonUtil.buildStringArray(null).isEmpty());
        List<JSONValue> jsonList = Arrays.asList(new JSONString("asdf"), new JSONNumber(2324523),
                JSONBoolean.getInstance(false));
        List<String> stringList = JsonUtil.buildStringArray(jsonList);
        assertEquals(3, stringList.size());
        assertEquals("asdf", stringList.get(0));
        assertEquals("2324523", stringList.get(1));
        assertEquals("false", stringList.get(2));
    }

    public void testBuildJsonArrayString() {
        assertNull(JsonUtil.buildJsonArrayString(null));
        assertEquals("[\"asdf\",\"2324523\",\"lkjlkjlkj\"]",
                JsonUtil.buildJsonArrayString(Arrays.asList("asdf", "2324523", "lkjlkjlkj")));
    }
}
