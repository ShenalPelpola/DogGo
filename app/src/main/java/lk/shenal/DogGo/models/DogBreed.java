package lk.shenal.DogGo.models;

import java.util.ArrayList;

public class DogBreed {
    private String breedId;
    private String breedName;
    private ArrayList<String> imageUrls;

    DogBreed(String breedId, String breedName) {
        this.breedId = breedId;
        this.breedName = breedName;
    }

    public String getBreedId() {
        return breedId;
    }

    public String getBreedName() {
        return breedName;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
