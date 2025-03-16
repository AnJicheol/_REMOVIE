package com.example.removie.kobis.cinema;


import com.example.removie.cinema.kobis.KOBISCinemaElement;
import com.example.removie.document.DocConnect;
import com.example.removie.document.DocConnection;
import com.example.removie.document.kobis.KOBISPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class KOBISCinemaElementTest {

    @InjectMocks private KOBISCinemaElement kobisCinemaElement;
    @Mock private DocConnect docConnect;
    @Mock private KOBISPage kobisPage;

    @Test
    public void getCinemaCodeElementsTest(){
        //given
        String movieCode1 = "20240737";
        String movieCode2 = "TEST";
        Document document1 = Jsoup.parse(
                """
                        KOBIS 관련 정보로 제거하였습니다
                        """);

        Document document2 = Jsoup.parse("TEST - TEST");
        DocConnection docConnection1 = mock(DocConnection.class);
        DocConnection docConnection2 = mock(DocConnection.class);
        when(kobisPage.cinemaPage(movieCode1)).thenReturn(docConnection1);
        when(kobisPage.cinemaPage(movieCode2)).thenReturn(docConnection2);

        when(docConnect.responseDoc(kobisPage.cinemaPage(movieCode1))).thenReturn(document1);
        when(docConnect.responseDoc(kobisPage.cinemaPage(movieCode2))).thenReturn(document2);

        //when
        Elements elements1 = kobisCinemaElement.getCinemaCodeElements(movieCode1);
        Elements elements2 = kobisCinemaElement.getCinemaCodeElements(movieCode2);

        //then
        assertFalse(elements1.isEmpty());
        assertTrue(elements2.isEmpty());

        System.out.println(elements1);

    }

}

