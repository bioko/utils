package it.engaged.utils.json;

import static org.junit.Assert.assertEquals;
import it.bioko.utils.fields.FieldNames;
import it.bioko.utils.fields.Fields;
import it.engaged.utils.domain.AnnotatedPersonExample;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Test;


//import static org.hamcrest.Matchers.*;

public class FieldsToJsonTest {

	@Test
	public void emptyFieldsToJson() throws Exception {
		Fields empty = Fields.empty();
		JSONObject object = new JSONObject();
		assertEquals(empty.asJson(), object.toJSONString());
	}

	@Test
	public void plainFieldsToJson() throws Exception {
		Fields fields = FieldsMother.twoFields();
		JSONObject object = new JSONObject();
		object.put(FieldNames.NAME, FieldNames.NAME_VALUE);
		object.put(FieldNames.SURNAME, FieldNames.SURNAME_VALUE);
		assertEquals(fields.asJson(), object.toJSONString());
	}

	@Test
	public void arrayOfDomainAntityFieldsToJson() throws Exception {
		ArrayList<AnnotatedPersonExample> domainEntityArray = new ArrayList<AnnotatedPersonExample>();
		AnnotatedPersonExample mattoEntity = new AnnotatedPersonExample(FieldsMother.twoFields());
		AnnotatedPersonExample paoloEntity = new AnnotatedPersonExample(FieldsMother.twoFields());
		domainEntityArray.add(mattoEntity);
		domainEntityArray.add(paoloEntity);

		String aKey = "domainEntityArray";
		Fields fields = Fields.single(aKey, domainEntityArray);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(aKey, domainEntityArray);
		String jsonString = JSONValue.toJSONString(jsonObject);

		assertEquals(fields.asJson(), jsonString);
	}

	@Test
	public void fieldsAndarrayOfDomainAntityToJson() throws Exception {
		ArrayList<AnnotatedPersonExample> domainEntityArray = new ArrayList<AnnotatedPersonExample>();
		AnnotatedPersonExample mattoEntity = new AnnotatedPersonExample(FieldsMother.twoFields());
		AnnotatedPersonExample paoloEntity = new AnnotatedPersonExample(FieldsMother.twoFields());
		domainEntityArray.add(mattoEntity);
		domainEntityArray.add(paoloEntity);

		String aKey = "domainEntityArray";
		Fields arrayOfDomainEntity = Fields.single(aKey, domainEntityArray);
		Fields fields = FieldsMother.twoFields().putAll(arrayOfDomainEntity);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(FieldNames.NAME, FieldNames.NAME_VALUE);
		jsonObject.put(FieldNames.SURNAME, FieldNames.SURNAME_VALUE);
		jsonObject.put(aKey, domainEntityArray);
		String jsonString = JSONValue.toJSONString(jsonObject);

		assertEquals(fields.asJson(), jsonString);
	}
}