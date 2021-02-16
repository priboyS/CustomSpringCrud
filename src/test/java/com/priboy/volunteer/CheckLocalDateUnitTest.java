package com.priboy.volunteer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static com.priboy.volunteer.util.CheckLocalDateUtil.checkLocalDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
public class CheckLocalDateUnitTest {

    @Test
    public void checkLocalDate_thenOk(){
        LocalDate localDate = checkLocalDate("2020-10-10");
        assertThat(localDate, equalTo(LocalDate.parse("2020-10-10")));
    }

    @Test
    public void checkNullLocalDate_thenOk(){
        LocalDate localDate = checkLocalDate("");
        assertNull(localDate);
    }

    @Test
    public void checkWrongLocalDate_thenOk(){
        boolean checkException = false;

        try{
            LocalDate localDate = checkLocalDate("10-10-10");
        }catch (Exception ex){
            checkException = true;
        }

        assertTrue(checkException);
    }

}
