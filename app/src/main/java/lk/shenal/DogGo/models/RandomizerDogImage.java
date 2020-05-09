package lk.shenal.DogGo.models;

import android.content.Intent;

import java.util.ArrayList;
import java.util.Random;

//This class is used to randomize the dog images. Separating the random generations from the presenters are done in this class.
public class RandomizerDogImage {
    private DogData dogData= new DogData();

    //Generating a random breed from the dogDate object
    public DogBreed getRandomDogBreed(){
        Random random = new Random();
        int breedIndex = random.nextInt(dogData.getDogBreeds().size());
        return dogData.getDogBreeds().get(breedIndex);
    }

    //Generates three unique breeds. Used in the searchDogActivity
    public ArrayList<DogBreed> getThreeUniqueRandomBreeds(){
        Random rand = new Random();
        ArrayList<DogBreed> threeRandomBreeds = new ArrayList<>();
        ArrayList<Integer> breedIndexes = new ArrayList<>();
        //checking whether the breed is available in the array. If not appends the unique breed.
        for(int i = 0; i < 3 ; i++){
            int breedIndex;
            do{
                breedIndex = rand.nextInt(dogData.getDogBreeds().size());
            }while (breedIndexes.contains(breedIndex));
            breedIndexes.add(breedIndex);
        }
        for(Integer breedIndex: breedIndexes){
            DogBreed randomBreed = dogData.getDogBreeds().get(breedIndex);
            threeRandomBreeds.add(randomBreed);
        }
        return threeRandomBreeds;
    }

    //Generates a unique breed name. Used in the searchDogActivity
    public String generateUniqueBreedName(ArrayList<DogBreed> dogbreeds){
        Random random = new Random();
        int index = random.nextInt(dogbreeds.size());
        return dogbreeds.get(index).getBreedName();
    }

    //Return a random number based on the size of the images.
    public int getImageIndex(DogBreed dogBreed){
        Random random = new Random();
        return random.nextInt(dogBreed.getImageUrls().size());
    }
    //Return a random number based on the size of the array. UTILITY METHOD.
    public int getArraySize(ArrayList arrayList){
        Random random = new Random();
        return  random.nextInt(arrayList.size());
    }
}
