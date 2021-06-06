package com.idealista.infrastructure;

import com.idealista.domain.ad.Ad;
import com.idealista.domain.repository.AdRepository;
import com.idealista.infrastructure.persistence.inMemoryAdRepositoryImpl;
import com.idealista.infrastructure.services.AdService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class IntegrationTest {


    private AdRepository adRepository = new inMemoryAdRepositoryImpl();
    @Autowired
    private AdService adService;

    @Test
    public void should_retrieve_data_from_db() throws Exception {

        List<Ad> all = adRepository.findAll();

        assertEquals(8,all.size());
        assertEquals("CHALET",all.get(0).getTypology().name());
        assertEquals("Este piso es una ganga, compra, compra, COMPRA!!!!!",all.get(0).getDescription().getText());
        assertEquals(2,all.get(4).getPictures().size());
        assertEquals("SD",all.get(4).getPictures().get(0).getQuality().name());
        assertEquals("SD",all.get(4).getPictures().get(1).getQuality().name());
        assertEquals("http://www.idealista.com/pictures/3",all.get(4).getPictures().get(0).getUrl().value());
        assertEquals("http://www.idealista.com/pictures/7",all.get(4).getPictures().get(1).getUrl().value());


    }

    @Test
    public void should_save_all_data_to_db(){
        List<Ad> beforePerform = adRepository.findAll();

        adService.performAdRank();
        List<Ad> afterPerform = adRepository.findAll();

        assertEquals(8,afterPerform.size());
        assertEquals("CHALET",afterPerform.get(0).getTypology().name());
        assertEquals("Este piso es una ganga, compra, compra, COMPRA!!!!!",afterPerform.get(0).getDescription().getText());
        assertEquals(2,afterPerform.get(4).getPictures().size());
        assertEquals("SD",afterPerform.get(4).getPictures().get(0).getQuality().name());
        assertEquals("SD",afterPerform.get(4).getPictures().get(1).getQuality().name());
        assertEquals("http://www.idealista.com/pictures/3",afterPerform.get(4).getPictures().get(0).getUrl().value());
        assertEquals("http://www.idealista.com/pictures/7",afterPerform.get(4).getPictures().get(1).getUrl().value());
        assertEquals(beforePerform.get(4).getDescription().getText(),afterPerform.get(4).getDescription().getText());
        assertNotEquals(beforePerform.get(1).getScore().value(),afterPerform.get(1).getScore().value());
    }



}
