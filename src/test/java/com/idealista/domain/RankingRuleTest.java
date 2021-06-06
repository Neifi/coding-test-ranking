package com.idealista.domain;

import com.idealista.domain.ad.*;
import com.idealista.domain.pictureEntity.Picture;
import com.idealista.domain.pictureEntity.QualityVO;
import com.idealista.domain.pictureEntity.UrlVO;
import com.idealista.domain.services.PointsCalculator;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static com.idealista.utils.AdUtils.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RankingRuleTest {

    @Autowired
    private PointsCalculator pointsCalculator;

    private Score scoreSpy;


    @Before
    public void setup() {
        Score score = new Score(0);
        scoreSpy = Mockito.spy(score);

    }


    @Test
    public void should_set_irrelevant_since_date_when_ad_score_reach_irrelevantThreshold() {
        Ad ad = new Ad(1, Typology.CHALET,
                new DescriptionVO(""),
                new HouseSizeVO(100),
                new Score(40),
                Collections.emptyList());
        pointsCalculator.calculate(ad);
        assertNotEquals(new Date(0),ad.getIrrelevantSince().getDate());

    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_score_is_less_than_zero() {
        Score scoreVO = new Score(-10);
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_url_is_invalid() {
        UrlVO urlVO = new UrlVO("w.w.com");
    }

    @Test
    public void when_ad_dont_have_any_image_score_should_decrease_10_points() {


        Ad noImageAd = new Ad(1, Typology.FLAT,
                new DescriptionVO("DESC"),
                new HouseSizeVO(100),
                new GardenSizeVO(100),
                scoreSpy,
                Collections.emptyList()
        );

        pointsCalculator.calculate(noImageAd);

        Mockito.verify(scoreSpy, times(1)).decrease(10);
    }

    @Test
    public void when_ad_have_one_hd_image_should_add_20_points() {
        Picture pictureEntity = new Picture(1, new UrlVO("https://test.com"), QualityVO.HD);
        Ad withHDImageAd = new Ad(1, Typology.FLAT,
                new DescriptionVO("DESC"),
                new HouseSizeVO(100),
                new GardenSizeVO(100),
                scoreSpy,
                Arrays.asList(pictureEntity));

        pointsCalculator.calculate(withHDImageAd);

        Mockito.verify(scoreSpy, times(1)).increase(20);


    }

    @Test
    public void when_ad_have_no_hd_image_should_add_10_points() {
        Picture pictureEntity = new Picture(1, new UrlVO("https://test.com"), QualityVO.SD);
        Ad noHDImageAd = new Ad(1, Typology.FLAT,
                new DescriptionVO("DESC"),
                new HouseSizeVO(100),
                new GardenSizeVO(100),
                this.scoreSpy,
                Arrays.asList(pictureEntity));


        pointsCalculator.calculate(noHDImageAd);

        Mockito.verify(scoreSpy, atLeast(1)).increase(10);
        Mockito.verify(scoreSpy, atMost(1)).increase(10);
    }

    @Test
    public void should_set_url_when_is_valid() {
        String url = "http://www.idealista.com/pictures/1";

        UrlVO urlVO = new UrlVO(url);

        Assertions.assertThat(urlVO.value()).isEqualTo(url);
    }

    @Test
    public void when_ad_has_description_text_should_add_5_points() {
        Picture pictureEntity = new Picture(1, new UrlVO("https://test.com"), QualityVO.HD);
        Ad ad = new Ad(1, Typology.FLAT,
                new DescriptionVO("useful description"),
                new HouseSizeVO(100),
                new GardenSizeVO(100),
                this.scoreSpy,
                Arrays.asList(pictureEntity));

        pointsCalculator.calculate(ad);
        Mockito.verify(scoreSpy, times(1)).increase(5);
    }

    @Test
    public void when_apartment_ad_has_description_text_size_between_20_and_49_should_add_10_points() {

        Ad ad = new Ad(1, Typology.FLAT,
                new DescriptionVO(TWENTY_WORDS_DESCRIPTION_TEXT),
                new HouseSizeVO(100),
                this.scoreSpy,
                Collections.emptyList());

        pointsCalculator.calculate(ad);
        Mockito.verify(scoreSpy, times(1)).increase(15);
    }

    @Test
    public void when_apartment_ad_has_description_text_size_is_equal_or_greater_than_50_should_add_30_points() {
        Ad ad = new Ad(1, Typology.FLAT,
                new DescriptionVO(FIFTY_WORDS_DESCRIPTION_TEXT),
                new HouseSizeVO(100),
                this.scoreSpy,
                Collections.emptyList());
        pointsCalculator.calculate(ad);
        Mockito.verify(scoreSpy, times(1)).increase(35);
    }

    @Test
    public void when_chalet_ad_has_description_text_size_greater_than_50_should_add_20_points() {
        Ad ad = new Ad(1, Typology.CHALET,
                new DescriptionVO(GREATER_THAN_FIFTY_WORDS_DESCRIPTION_TEXT),
                new HouseSizeVO(null),
                this.scoreSpy,
                Collections.emptyList());

        pointsCalculator.calculate(ad);
        Mockito.verify(scoreSpy, times(1)).increase(25);
    }

    @Test
    public void should_ad_5_points_per_description_text_keyword_match() {

        Ad ad = new Ad(1, Typology.CHALET,
                new DescriptionVO("Luminoso,Nuevo,Céntrico,Reformado,Ático"),
                null,
                null,
                scoreSpy,
                Collections.emptyList());


        pointsCalculator.calculate(ad);

        verify(scoreSpy, times(1)).increase(30);

    }


    @Test
    public void when_apartment_ad_meet_completeness_criteria_should_add_40_points() {
        Picture pictureEntity = new Picture(1, new UrlVO("https://test.com"), QualityVO.HD);
        Ad ad = new Ad(1,
                Typology.FLAT,
                new DescriptionVO("Luminoso,Nuevo,Céntrico,Reformado,Ático " + FIFTY_WORDS_DESCRIPTION_TEXT),
                new HouseSizeVO(100),
                this.scoreSpy,
                Arrays.asList(pictureEntity));

        pointsCalculator.calculate(ad);

        Mockito.verify(scoreSpy, times(1)).increase(40);

    }

    @Test
    public void when_chalet_ad_meet_completeness_criteria_should_add_40_points() {
        Picture pictureEntity = new Picture(1, new UrlVO("https://test.com"), QualityVO.HD);
        Ad ad = new Ad(1, Typology.CHALET,
                new DescriptionVO("Luminoso,Nuevo,Céntrico,Reformado,Ático " + GREATER_THAN_FIFTY_WORDS_DESCRIPTION_TEXT),
                new HouseSizeVO(100),
                new GardenSizeVO(100),
                this.scoreSpy,
                Arrays.asList(pictureEntity));

        pointsCalculator.calculate(ad);

        Mockito.verify(scoreSpy, times(1)).increase(40);

    }

    @Test
    public void when_garage_ad_meet_completeness_criteria_should_add_40_points() {
        Picture pictureEntity = new Picture(1, new UrlVO("https://test.com"), QualityVO.HD);
        Ad ad = new Ad(1, Typology.GARAGE,
                new DescriptionVO(""),
                new HouseSizeVO(100),
                new GardenSizeVO(0),
                this.scoreSpy,
                Arrays.asList(pictureEntity));

        pointsCalculator.calculate(ad);

        Mockito.verify(scoreSpy, times(1)).increase(40);

    }

    @Test
    public void when_garage_ad_dont_meet_completeness_criteria_should_add_40_points() {
        Ad ad = new Ad(1, Typology.GARAGE,
                new DescriptionVO(""),
                new HouseSizeVO(100),
                this.scoreSpy,
                Collections.emptyList());

        pointsCalculator.calculate(ad);

        Mockito.verify(scoreSpy, times(1)).decrease(40);

    }

    @Test
    public void when_chalet_ad_dont_meet_completeness_criteria_should_add_40_points() {
        Picture pictureEntity = new Picture(1, new UrlVO("https://test.com"), QualityVO.HD);
        Ad ad = new Ad(1, Typology.CHALET,
                new DescriptionVO("Luminoso,Nuevo,Céntrico,Reformado,Ático " + GREATER_THAN_FIFTY_WORDS_DESCRIPTION_TEXT),
                new HouseSizeVO(100),
                this.scoreSpy,
                Arrays.asList(pictureEntity));

        pointsCalculator.calculate(ad);

        Mockito.verify(scoreSpy, times(1)).decrease(40);

    }

    @Test
    public void when_apartment_ad_dont_meet_completeness_criteria_should_add_40_points() {
        Picture pictureEntity = new Picture(1, new UrlVO("https://test.com"), QualityVO.HD);
        Ad ad = new Ad(1,
                Typology.FLAT,
                new DescriptionVO("Luminoso,Nuevo,Céntrico,Reformado,Ático " + FIFTY_WORDS_DESCRIPTION_TEXT),
                new HouseSizeVO(100),
                this.scoreSpy,
                Collections.emptyList());

        pointsCalculator.calculate(ad);

        Mockito.verify(scoreSpy, times(1)).decrease(40);

    }

    @Test
    public void ad_must_dont_have_more_than_max_points() {
        Picture pictureEntity = new Picture(1, new UrlVO("https://test.com"), QualityVO.HD);
        Picture picture2 = new Picture(2, new UrlVO("https://test.com"), QualityVO.HD);
        Picture picture3 = new Picture(3, new UrlVO("https://test.com"), QualityVO.HD);
        Picture picture4 = new Picture(4, new UrlVO("https://test.com"), QualityVO.HD);
        Picture picture5 = new Picture(5, new UrlVO("https://test.com"), QualityVO.HD);
        Score spyScore = spy(new Score(0));
        Ad ad = new Ad(1,
                Typology.CHALET,
                new DescriptionVO(GREATER_THAN_FIFTY_WORDS_DESCRIPTION_TEXT),
                new HouseSizeVO(100),
                new GardenSizeVO(100),
                new Score(0),
                Arrays.asList(pictureEntity, picture2, picture3, picture4, picture5));

        pointsCalculator.calculate(ad);

        assertEquals(MAX_SCORE_POINTS, ad.getScore().value());
    }


}
