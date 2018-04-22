package com.suman.spring.batch.helper;

import com.suman.spring.batch.exception.BatchResourceException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

@PrepareForTest({FreeMarkerEvaluator.class})
@RunWith(PowerMockRunner.class)
public class FreeMarkerEvaluatorTest {

    private FreeMarkerEvaluator freeMarkerEvaluator;

    @Mock
    private Configuration configuration;

    @Rule
    ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        freeMarkerEvaluator = new FreeMarkerEvaluator();
        setValuesForMemberFields(freeMarkerEvaluator, "configuration", configuration);
    }

    @Test
    public void shouldEvaluateTemplate() throws Exception {
        Object input = new Object();
        String templateName = "Vital Signs";
        StringWriter stringWriter = Mockito.mock(StringWriter.class);
        PowerMockito.whenNew(StringWriter.class).withNoArguments().thenReturn(stringWriter);
        Template template = Mockito.mock(Template.class);
        when(configuration.getTemplate(templateName)).thenReturn(template);
        HashMap<String, Object> hashMap = mock(HashMap.class);
        PowerMockito.whenNew(HashMap.class).withNoArguments().thenReturn(hashMap);
        String expectedOutput = "outputValue";
        when(stringWriter.toString()).thenReturn(expectedOutput);

        String actualOutput = freeMarkerEvaluator.evaluate(templateName, input);

        assertEquals(expectedOutput, actualOutput);
        verify(configuration, times(1)).getTemplate(templateName);
        verify(hashMap, times(1)).put("input", input);
        verify(template, times(1)).process(hashMap, stringWriter);
    }

    @Test
    public void shouldThrowBatchResourceException() throws Exception {
        Object input = new Object();
        String templateName = "Vital Signs";
        BatchResourceException batchResourceException = Mockito.mock(BatchResourceException.class);
        when(configuration.getTemplate(templateName)).thenThrow(batchResourceException);

        expectedException.expect(BatchResourceException.class);
        expectedException.expectMessage(
                String.format("Unable to continue generating a the template with name [%s]", templateName));

        freeMarkerEvaluator.evaluate(templateName, input);
        verify(configuration, times(1)).getTemplate(templateName);
    }

    private static void setValuesForMemberFields(Object classInstance, String fieldName, Object valueForMemberField) throws Exception {
        Field field = classInstance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(classInstance, valueForMemberField);
    }
}
