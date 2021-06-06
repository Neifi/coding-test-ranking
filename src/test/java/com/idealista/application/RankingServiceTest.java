package com.idealista.application;

import com.idealista.application.services.RankingService;
import com.idealista.domain.ad.*;
import com.idealista.domain.pictureEntity.Picture;
import com.idealista.domain.pictureEntity.QualityVO;
import com.idealista.domain.pictureEntity.UrlVO;
import com.idealista.domain.repository.AdRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.idealista.utils.AdUtils.GREATER_THAN_FIFTY_WORDS_DESCRIPTION_TEXT;
import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RankingServiceTest {

    @Autowired
    RankingService rankingService;

    @MockBean
    AdRepository adRepository;

    private final int RELEVANT_THRESHOLD = 40;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_return_relevant_ads_order_asc_by_score() {
        Ad mostRelevantAd = new Ad(1, Typology.GARAGE,
                new DescriptionVO("DESC"),
                new HouseSizeVO(100),
                new Score(100),
                Collections.emptyList());

        Ad adTwo = new Ad(2, Typology.CHALET,
                new DescriptionVO("DESC"),
                new HouseSizeVO(100),
                new GardenSizeVO(100),
                new Score(70),
                Collections.emptyList());

        Ad adThree = new Ad(3, Typology.FLAT,
                new DescriptionVO("DESC"),
                new HouseSizeVO(100),
                new Score(40),
                Collections.emptyList());


        when(adRepository.findAll()).thenReturn(Arrays.asList(adThree, mostRelevantAd, adTwo));

        List<Ad> relevantAds = rankingService.getRelevantAds();

        assertEquals(2, relevantAds.size());
        assertTrue(relevantAds.get(0).isRelevant());
        assertTrue(relevantAds.get(1).isRelevant());
        assertTrue(relevantAds.get(0).getScore().value() > RELEVANT_THRESHOLD);
        assertTrue(relevantAds.get(1).getScore().value() > RELEVANT_THRESHOLD);
        assertEquals(new Date(0),relevantAds.get(0).getIrrelevantSince().getDate());
        assertEquals(new Date(0),relevantAds.get(1).getIrrelevantSince().getDate());
    }

    @Test
    public void should_return_irrelevant_ads() {
        Ad relevantAd = new Ad(1, Typology.GARAGE,
                new DescriptionVO("DESC"),
                new HouseSizeVO(100),
                new Score(100),
                Collections.emptyList());

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


        when(adRepository.findAll()).thenReturn(Arrays.asList(adThree, relevantAd, adTwo));

        List<Ad> relevantAds = rankingService.getIrelevantAds();

        assertEquals(2, relevantAds.size());
        assertFalse(relevantAds.get(0).isRelevant());
        assertFalse(relevantAds.get(1).isRelevant());
        assertTrue(relevantAds.get(0).getScore().value() <= RELEVANT_THRESHOLD);
        assertTrue(relevantAds.get(1).getScore().value() <= RELEVANT_THRESHOLD);

    }

    @Test
    public void should_calculate_scores_for_ads() {
        Picture pictureEntity = new Picture(1, new UrlVO("https://test.com"), QualityVO.HD);
        Picture picture2 = new Picture(2, new UrlVO("https://test.com"), QualityVO.HD);
        Picture picture3 = new Picture(3, new UrlVO("https://test.com"), QualityVO.HD);
        Picture picture4 = new Picture(4, new UrlVO("https://test.com"), QualityVO.HD);
        Picture picture5 = new Picture(5, new UrlVO("https://test.com"), QualityVO.HD);
        Score spyScore = spy(new Score(0));
        Ad ad = new Ad(1, Typology.FLAT,
                new DescriptionVO(GREATER_THAN_FIFTY_WORDS_DESCRIPTION_TEXT),
                new HouseSizeVO(100),
                new Score(0),
                Arrays.asList(pictureEntity, picture2, picture3, picture4, picture5));

        rankingService.calculateScore(Arrays.<Ad>asList(ad));

        assertEquals(ad.getScore().value(), 100);

    }

}
