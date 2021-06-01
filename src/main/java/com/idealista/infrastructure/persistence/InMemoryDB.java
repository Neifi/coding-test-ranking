package com.idealista.infrastructure.persistence;

import com.idealista.domain.ad.AdVO;
import com.idealista.domain.picture.PictureVO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryDB {
    private static InMemoryDB inMemoryDB = null;

    private ArrayList<AdVO> ads = new ArrayList<>();
    private ArrayList<PictureVO> pictures = new ArrayList<>();

    private InMemoryDB() {
    /*    ads = new ArrayList<AdVO>();
        ads.add(new AdVO(1, "CHALET", "Este piso es una ganga, compra, compra, COMPRA!!!!!", Collections.<Integer>emptyList(), 300, null, null, null));
        ads.add(new AdVO(2, "FLAT", "Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo", Arrays.asList(4), 300, null, null, null));
        ads.add(new AdVO(3, "CHALET", "", Arrays.asList(2), 300, null, null, null));
        ads.add(new AdVO(4, "FLAT", "Ático céntrico muy luminoso y recién reformado, parece nuevo", Arrays.asList(5), 300, null, null, null));
        ads.add(new AdVO(5, "FLAT", "Pisazo,", Arrays.asList(3, 8), 300, null, null, null));
        ads.add(new AdVO(6, "GARAGE", "", Arrays.asList(6), 300, null, null, null));
        ads.add(new AdVO(7, "GARAGE", "Garaje en el centro de Albacete", Collections.<Integer>emptyList(), 300, null, null, null));
        ads.add(new AdVO(8, "CHALET", "Maravilloso chalet situado en lAs afueras de un pequeño pueblo rural. El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", Arrays.asList(1, 7), 300, null, null, null));

        pictures = new ArrayList<PictureVO>();
        pictures.add(new PictureVO(1, "http://www.idealista.com/pictures/1", "SD"));
        pictures.add(new PictureVO(2, "http://www.idealista.com/pictures/2", "HD"));
        pictures.add(new PictureVO(3, "http://www.idealista.com/pictures/3", "SD"));
        pictures.add(new PictureVO(4, "http://www.idealista.com/pictures/4", "HD"));
        pictures.add(new PictureVO(5, "http://www.idealista.com/pictures/5", "SD"));
        pictures.add(new PictureVO(6, "http://www.idealista.com/pictures/6", "SD"));
        pictures.add(new PictureVO(7, "http://www.idealista.com/pictures/7", "SD"));*/
    }

    public static InMemoryDB getInstance(){
        if(inMemoryDB == null){
            inMemoryDB = new InMemoryDB();
        }

        return inMemoryDB;
    }

    public List<AdVO> getAds() {
        return Collections.unmodifiableList(this.ads);
    }

    public List<PictureVO> getPictures() {
        return Collections.unmodifiableList(this.pictures);
    }

    public void addAd(AdVO ad) {
        this.ads.add(ad);
    }

    public void adPicture(PictureVO picture) {
        this.pictures.add(picture);
    }
}
