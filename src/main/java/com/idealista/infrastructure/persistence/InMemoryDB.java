package com.idealista.infrastructure.persistence;

import com.idealista.infrastructure.persistence.model.Ad;
import com.idealista.infrastructure.persistence.model.Picture;
import org.apache.commons.collections.list.SynchronizedList;

import java.util.*;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class InMemoryDB {
    private static InMemoryDB inMemoryDB = null;

    private List<Ad> ads;
    private ArrayList<Picture> pictures = new ArrayList<>();

    private InMemoryDB() {
        ads =  Collections.synchronizedList(new ArrayList<Ad>());
        ads.add(new Ad(1, "CHALET", "Este piso es una ganga, compra, compra, COMPRA!!!!!", Collections.<Integer>emptyList(), 300, null, null, null));
        ads.add(new Ad(2, "FLAT", "Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo", Arrays.asList(4), 300, null, null, null));
        ads.add(new Ad(3, "CHALET", "", Arrays.asList(2), 300, null, null, null));
        ads.add(new Ad(4, "FLAT", "Ático céntrico muy luminoso y recién reformado, parece nuevo", Arrays.asList(5), 300, null, null, null));
        ads.add(new Ad(5, "FLAT", "Pisazo,", Arrays.asList(3, 7), 300, null, null, null));
        ads.add(new Ad(6, "GARAGE", "", Arrays.asList(6), 300, null, null, null));
        ads.add(new Ad(7, "GARAGE", "Garaje en el centro de Albacete", Collections.<Integer>emptyList(), 300, null, 100, null));
        ads.add(new Ad(8, "CHALET", "Maravilloso chalet situado en lAs afueras de un pequeño pueblo rural. El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", Arrays.asList(1, 7), 300, null, null, null));

        pictures = new ArrayList<Picture>();
        pictures.add(new Picture(1, "http://www.idealista.com/pictures/1", "SD"));
        pictures.add(new Picture(2, "http://www.idealista.com/pictures/2", "HD"));
        pictures.add(new Picture(3, "http://www.idealista.com/pictures/3", "SD"));
        pictures.add(new Picture(4, "http://www.idealista.com/pictures/4", "HD"));
        pictures.add(new Picture(5, "http://www.idealista.com/pictures/5", "SD"));
        pictures.add(new Picture(6, "http://www.idealista.com/pictures/6", "SD"));
        pictures.add(new Picture(7, "http://www.idealista.com/pictures/7", "SD"));
    }

    public static InMemoryDB getInstance(){
        if(inMemoryDB == null){
            inMemoryDB = new InMemoryDB();
        }

        return inMemoryDB;
    }

    public List<Ad> getAd() {
        return Collections.unmodifiableList(this.ads);
    }

    public List<Picture> getPictures() {

        return Collections.unmodifiableList(this.pictures);
    }

    public void addAd(Ad ad) {
        this.ads.add(ad);
    }

    public void adPicture(Picture picture) {
        this.pictures.add(picture);
    }

    public void deleteAll() {
        ads.clear();
    }

    public void saveAll(List<Ad> adsToSave) {


        ArrayList<Ad> duplicates = removeDuplicates(adsToSave);
        this.ads.removeAll(duplicates);
        this.ads.addAll(adsToSave);


    }

    private ArrayList<Ad> removeDuplicates(List<Ad> adsToSave) {
        ArrayList<Ad> duplicates = new ArrayList<>();
        for (Ad ad:ads){
            for (Ad adTs: adsToSave){
                if (ad.getId().equals(adTs.getId())){
                    duplicates.add(ad);
                }
            }
        }

        return duplicates;
    }
}
