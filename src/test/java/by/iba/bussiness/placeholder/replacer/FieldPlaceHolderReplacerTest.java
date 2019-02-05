package by.iba.bussiness.placeholder.replacer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class FieldPlaceHolderReplacerTest {

    @InjectMocks
    private FieldPlaceHolderReplacer fieldPlaceHolderReplacer;

    @Test
    public void testReplaceFieldPlaceHolders() {
        //given
        String firstPlaceHolder = "${first}";
        String secondPlaceHolder = "${second}";
        String firstPlaceHolderContent = "FIRST_CONTENT";
        String secondPlaceHolderContent = "SECOND_CONTENT";
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put(firstPlaceHolder, firstPlaceHolderContent);
        placeHolders.put(secondPlaceHolder, secondPlaceHolderContent);
        String field = "ABC ${first} DEF ${second} ABC${first} DEF${second}${first}${second}";
        String fieldWithReplacedPlaceHolders = "ABC FIRST_CONTENT DEF SECOND_CONTENT ABCFIRST_CONTENT DEFSECOND_CONTENTFIRST_CONTENTSECOND_CONTENT";
        //when
        String expected = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, field);
        //then
        Assert.assertEquals(expected, fieldWithReplacedPlaceHolders);

    }
}