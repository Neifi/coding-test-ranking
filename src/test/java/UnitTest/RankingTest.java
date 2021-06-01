package UnitTest;

import com.idealista.application.ComputeAdPoints;
import com.idealista.application.ListRelevantAds;
import com.idealista.domain.ad.*;
import com.idealista.domain.picture.PictureVO;
import com.idealista.domain.picture.QualityVO;
import com.idealista.domain.picture.UrlVO;
import com.idealista.domain.repository.AdRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class RankingTest {

    @Mock
    private AdRepository adRepository;

    private ListRelevantAds listRelevantAds;

    private final ComputeAdPoints computeAdPoints = new ComputeAdPoints();

    private final String TWENTY_WORDS_DESCRIPTION_TEXT = "Lorem ipsum dolor sit amet consectetur adipiscing elit, ridiculus cum felis ante malesuada viverra, suscipit natoque sollicitudin pulvinar himenaeos molestie.";
    private final String FIFTY_WORDS_DESCRIPTION_TEXT = "Lorem ipsum dolor sit amet consectetur adipiscing elit tellus proin, aenean nascetur dictumst ullamcorper consequat orci primis felis congue, facilisi sem aliquet sapien quis fermentum urna varius. Sociosqu enim donec dapibus auctor pretium hendrerit tempor scelerisque, justo inceptos morbi faucibus convallis pharetra nibh, cum tristique elementum tincidunt dictum massa condimentum.";
    private final String GREATER_THAN_FIFTY_WORDS_DESCRIPTION_TEXT = " Lorem ipsum dolor sit amet consectetur adipiscing elit odio, mus massa congue mollis netus ultricies accumsan diam cras, egestas et etiam nibh cursus commodo ut. Vulputate non hendrerit eleifend condimentum, duis proin lacus orci, dapibus leo viverra. Velit maecenas nullam urna lectus pellentesque primis mauris augue, ad aliquam erat ultrices ante ridiculus facilisi, ullamcorper interdum a inceptos eros nunc magnis.";
    private ScoreVO scoreSpy;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        listRelevantAds = new ListRelevantAds(adRepository);
        ScoreVO score = new ScoreVO(0);
        scoreSpy = Mockito.spy(score);
    }

    @Test
    public void should_order_ads_in_completeness_order(){
        AdVO mostRelevantAd = new AdVO(1,TypologyVO.PISO,
                new DescriptionVO(),
                new HouseSizeVO(100L),
                new GardenSizeVO(100L),
                new ScoreVO(100),
                new IrrelevantSinceVO(),
                new PictureVO());

        AdVO adTwo = new AdVO(1,TypologyVO.PISO,
                new DescriptionVO(),
                new HouseSizeVO(100L),
                new GardenSizeVO(100L),
                new ScoreVO(70),
                new IrrelevantSinceVO(),
                new PictureVO());

        AdVO adThree = new AdVO(1,TypologyVO.PISO,
                new DescriptionVO(),
                new HouseSizeVO(100L),
                new GardenSizeVO(100L),
                new ScoreVO(50),
                new IrrelevantSinceVO(),
                new PictureVO());
        AdVO adFour = new AdVO(1,TypologyVO.PISO,
                new DescriptionVO(),
                new HouseSizeVO(100L),
                new GardenSizeVO(100L),
                new ScoreVO(40),
                new IrrelevantSinceVO(),
                new PictureVO());
        AdVO lessRelevantAd = new AdVO(1,TypologyVO.PISO,
                new DescriptionVO(),
                new HouseSizeVO(100L),
                new GardenSizeVO(100L),
                new ScoreVO(10),
                new IrrelevantSinceVO(),
                new PictureVO());
        List<AdVO> ads = new ArrayList<>();

        ads.add(lessRelevantAd);
        ads.add(adFour);
        ads.add(adThree);
        ads.add(mostRelevantAd);
        ads.add(adTwo);

        Mockito.when(adRepository.findAll()).thenReturn(ads);
        List<AdVO> relevantAds = listRelevantAds.getAdsInOrder();

        assertEquals(relevantAds.get(0),mostRelevantAd);
        assertEquals(relevantAds.get(1),adTwo);
        assertEquals(relevantAds.get(2),adThree);
        assertEquals(relevantAds.get(3),adFour);
        assertEquals(relevantAds.get(4),lessRelevantAd);


    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_score_is_less_than_zero(){
        ScoreVO scoreVO = new ScoreVO(-10);
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_url_is_invalid(){
        UrlVO urlVO = new UrlVO("w.w.com");
    }

    @Test
    public void when_ad_dont_have_any_image_score_should_decrease_10_points(){


        AdVO noImageAd = new AdVO(1,TypologyVO.PISO,
                new DescriptionVO(),
                new HouseSizeVO(100L),
                new GardenSizeVO(100L),
                scoreSpy,
                new IrrelevantSinceVO());

        ComputeAdPoints computeAdPoints = new ComputeAdPoints();
        computeAdPoints.computeImageScore(noImageAd);

        Mockito.verify(scoreSpy,atLeast(1)).decrease(10);
        Mockito.verify(scoreSpy,atMost(1)).decrease(10);
    }

    @Test
    public void when_ad_have_one_hd_image_should_add_20_points(){
        PictureVO picture = new PictureVO(1,new UrlVO("https://test.com"),QualityVO.HD);
        AdVO withHDImageAd = new AdVO(1,TypologyVO.PISO,
                new DescriptionVO(),
                new HouseSizeVO(100L),
                new GardenSizeVO(100L),
                scoreSpy,
                new IrrelevantSinceVO(),
                picture);

        ComputeAdPoints computeAdPoints = new ComputeAdPoints();
        computeAdPoints.computeImageScore(withHDImageAd);

        Mockito.verify(scoreSpy,atLeast(1)).increase(20);
        Mockito.verify(scoreSpy,atMost(1)).increase(20);


    }
    @Test
    public void when_ad_have_no_hd_image_should_add_10_points(){
        PictureVO picture = new PictureVO(1,new UrlVO("https://test.com"),QualityVO.NO_HD);
        AdVO noHDImageAd = new AdVO(1,TypologyVO.PISO,
                new DescriptionVO(),
                new HouseSizeVO(100L),
                new GardenSizeVO(100L),
                this.scoreSpy,
                new IrrelevantSinceVO(),
                picture);


        computeAdPoints.computeImageScore(noHDImageAd);

        Mockito.verify(scoreSpy,atLeast(1)).increase(10);
        Mockito.verify(scoreSpy,atMost(1)).increase(10);


    }

    @Test
    public void should_set_url_when_is_valid(){
        String url = "http://www.idealista.com/pictures/1";

        UrlVO urlVO = new UrlVO(url);

        Assertions.assertThat(urlVO.value()).isEqualTo(url);
    }

    @Test
    public void when_ad_has_description_text_should_add_5_points(){
        PictureVO picture = new PictureVO(1,new UrlVO("https://test.com"),QualityVO.HD);
        AdVO withImageAd = new AdVO(1,TypologyVO.PISO,
                new DescriptionVO("useful description"),
                new HouseSizeVO(100L),
                new GardenSizeVO(100L),
                this.scoreSpy,
                new IrrelevantSinceVO(),
                picture);

        computeAdPoints.computeDescriptionScore(withImageAd);

        Mockito.verify(scoreSpy,atMost(1)).increase(5);
        Mockito.verify(scoreSpy,atLeast(1)).increase(5);

    }
    @Test
    public void when_apartment_ad_has_description_text_size_between_20_and_49_should_add_10_points(){

        AdVO noImageAd = new AdVO(1,TypologyVO.PISO,
                new DescriptionVO(TWENTY_WORDS_DESCRIPTION_TEXT),
                new HouseSizeVO(100L),
                new GardenSizeVO(100L),
                this.scoreSpy,
                new IrrelevantSinceVO());
        computeAdPoints.computeDescriptionScore(noImageAd);

        Mockito.verify(scoreSpy,atMost(1)).increase(10);
        Mockito.verify(scoreSpy,atLeast(1)).increase(10);

    }

    @Test
    public void when_apartment_ad_has_description_text_size_is_equal_or_greater_than_50_should_add_30_points(){
        AdVO noImageAd = new AdVO(1,TypologyVO.PISO,
                new DescriptionVO(FIFTY_WORDS_DESCRIPTION_TEXT),
                new HouseSizeVO(100L),
                new GardenSizeVO(100L),
                this.scoreSpy,
                new IrrelevantSinceVO());
        computeAdPoints.computeDescriptionScore(noImageAd);

        Mockito.verify(scoreSpy,atMost(1)).increase(30);
        Mockito.verify(scoreSpy,atLeast(1)).increase(30);
    }

    @Test
    public void when_chalet_ad_has_description_text_size_greater_than_50_should_add_20_points(){
        AdVO noImageAd = new AdVO(1,TypologyVO.CHALET,
                new DescriptionVO(GREATER_THAN_FIFTY_WORDS_DESCRIPTION_TEXT),
                new HouseSizeVO(100L),
                new GardenSizeVO(100L),
                this.scoreSpy,
                new IrrelevantSinceVO());
        computeAdPoints.computeDescriptionScore(noImageAd);

        Mockito.verify(scoreSpy,atLeast(1)).increase(20);
        Mockito.verify(scoreSpy,atMost(1)).increase(20);
    }

    @Test
    public void should_ad_5_points_per_description_text_keyword_match(){
        AdVO noImgAd = new AdVO(1,TypologyVO.CHALET,
                new DescriptionVO("Luminoso,Nuevo,Céntrico,Reformado,Ático"),
                new HouseSizeVO(100L),
                new GardenSizeVO(100L),
                this.scoreSpy,
                new IrrelevantSinceVO());

        computeAdPoints.computeDescriptionScore(noImgAd);

        Mockito.verify(scoreSpy,atMost(1)).increase(25);
        Mockito.verify(scoreSpy,atLeast(1)).increase(25);

    }


    //pisos: desc,almenos 1 foto,tamaño de vivienda
    //chalet: desc,almenos 1 foto,tamaño de vivienda y jardin
    //garaje: almenos 1 foto,tamaño de vivienda
    @Test
    public void when_apartment_ad_meet_completeness_criteria_should_add_40_points(){
        PictureVO picture = new PictureVO(1,new UrlVO("https://test.com"),QualityVO.HD);
        AdVO ad =
                new AdVO(1
                        ,TypologyVO.PISO,
                new DescriptionVO("Luminoso,Nuevo,Céntrico,Reformado,Ático "+FIFTY_WORDS_DESCRIPTION_TEXT),
                new HouseSizeVO(100L),
                this.scoreSpy,
                new IrrelevantSinceVO(),
                picture);

        computeAdPoints.computeCompleteness(ad);

        Mockito.verify(scoreSpy,atMost(1)).increase(40);
        Mockito.verify(scoreSpy,atLeast(1)).increase(40);

    }

    @Test
    public void when_chalet_ad_meet_completeness_criteria_should_add_40_points(){
        PictureVO picture = new PictureVO(1,new UrlVO("https://test.com"),QualityVO.HD);
        AdVO ad =
                new AdVO(1,TypologyVO.CHALET,
                        new DescriptionVO("Luminoso,Nuevo,Céntrico,Reformado,Ático "+ GREATER_THAN_FIFTY_WORDS_DESCRIPTION_TEXT),
                        new HouseSizeVO(100L),
                        new GardenSizeVO(100L),
                        this.scoreSpy,
                        new IrrelevantSinceVO(),
                        picture);

        computeAdPoints.computeCompleteness(ad);

        Mockito.verify(scoreSpy,atLeast(1)).increase(40);
        Mockito.verify(scoreSpy,atMost(1)).increase(40);

    }

    @Test
    public void when_garage_ad_meet_completeness_criteria_should_add_40_points(){
        PictureVO picture = new PictureVO(1,new UrlVO("https://test.com"),QualityVO.HD);
        AdVO ad =
                new AdVO(1,TypologyVO.GARAJE,
                        new DescriptionVO(""),
                        new HouseSizeVO(100L),
                        new GardenSizeVO(0L),
                        this.scoreSpy,
                        new IrrelevantSinceVO(),
                        picture);

        computeAdPoints.computeCompleteness(ad);

        Mockito.verify(scoreSpy,atLeast(1)).increase(40);
        Mockito.verify(scoreSpy,atMost(1)).increase(40);

    }

}
