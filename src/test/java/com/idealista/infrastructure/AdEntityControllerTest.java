package com.idealista.infrastructure;

import com.idealista.application.services.RankingService;
import com.idealista.domain.ad.*;
import com.idealista.domain.repository.AdRepository;
import com.idealista.infrastructure.api.AdsController;
import com.idealista.infrastructure.services.AdService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AdsController.class)
public class AdEntityControllerTest {

    @MockBean
    RankingService rankingService;

    @MockBean
    AdRepository repository;

    @MockBean
    AdService adService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void should_return_list_of_irrelevant_ads_with_http_status_200() throws Exception {
        Ad adTwo = new Ad(2, Typology.CHALET,
                new DescriptionVO("DESC"),
                new HouseSizeVO(100),
                new GardenSizeVO(100),
                new Score(30),
                Collections.emptyList());
        Ad adThree = new Ad(3, Typology.FLAT,
                new DescriptionVO("DESC"),
                new HouseSizeVO(100),
                new Score(40),
                Collections.emptyList());

        Mockito.when(rankingService.getIrelevantAds()).thenReturn(Arrays.<Ad>asList(adTwo, adThree));

        mockMvc.perform(get("/ad/quality")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].score", is(30)))
                .andExpect(jsonPath("$[1].score", is(40)));



    }

    @Test
    public void should_return_list_of_relevant_ads_with_http_status_200() throws Exception {
        Ad adTwo = new Ad(2, Typology.CHALET,
                new DescriptionVO("DESC"),
                new HouseSizeVO(100),
                new GardenSizeVO(100),
                new Score(100),
                Collections.emptyList());
        Ad adThree = new Ad(3, Typology.FLAT,
                new DescriptionVO("DESC"),
                new HouseSizeVO(100),
                new Score(90),
            Collections.emptyList());
        Mockito.when(rankingService.getRelevantAds()).thenReturn(Arrays.<Ad>asList(adTwo, adThree));

        mockMvc.perform(get("/ad/public")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    public void should_return_http_status_204_when_no_ads_to_return() throws Exception {

        Mockito.when(rankingService.getRelevantAds()).thenReturn(Collections.emptyList());
        Mockito.when(rankingService.getIrelevantAds()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/ad/public")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/ad/quality")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    public void should_calculate_ad_ranking_and_response_with_200() throws Exception {
        Ad adTwo = new Ad(2, Typology.CHALET,
                new DescriptionVO("DESC"),
                new HouseSizeVO(100),
                new GardenSizeVO(100),
                new Score(100),
                Collections.emptyList());
        Ad adThree = new Ad(3, Typology.FLAT,
                new DescriptionVO("DESC"),
                new HouseSizeVO(100),
                new Score(90),
                Collections.emptyList());

        List<Ad> ads = Arrays.<Ad>asList(adTwo, adThree);
        Mockito.when(repository.findAll()).thenReturn(ads);
        Mockito.doNothing().when(repository).saveAll(ads);

        mockMvc.perform(post("/ad/calculate")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }




}
