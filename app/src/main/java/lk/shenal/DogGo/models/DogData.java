package lk.shenal.DogGo.models;

import java.util.ArrayList;

//This class is used to setup the dog breed data.
public class DogData {
    private ArrayList<DogBreed> dogBreeds;//used to store all the dog breed data.
    public DogData() {
        dogBreeds = new ArrayList<>();
        dogBreeds.add(new DogBreed("n02106662_","German shepard"));
        dogBreeds.add(new DogBreed("n02110185_","Husky"));
        dogBreeds.add(new DogBreed("n02110958_" , "Pug"));
        dogBreeds.add(new DogBreed("n02088364_" ,"Beagle"));
        dogBreeds.add(new DogBreed("n02108089_" ,"Boxer"));
        dogBreeds.add(new DogBreed("n02107142_" , "Doberman"));
        dogBreeds.add(new DogBreed("n02106550_" ,"Rottweiler"));
        dogBreeds.add(new DogBreed("n02109525_" , "St Bernard"));
        dogBreeds.add(new DogBreed("n02099712_" , "Labrador"));
        dogBreeds.add(new DogBreed("n02099601_" , "Golden Retriever"));
        dogBreeds.add(new DogBreed("n02085620_" , "Chihuahua"));
        dogBreeds.add(new DogBreed("n02086910_" , "Papillon"));
        dogBreeds.add(new DogBreed("n02091831_" , "Saluki"));
        dogBreeds.add(new DogBreed("n02115641_" , "Dingo"));
        dogBreeds.add(new DogBreed("n02112137_" , "Chow"));
        dogBreeds.add(new DogBreed("n02112018_" , "Pomeranian"));
        dogBreeds.add(new DogBreed("n02100583_" , "Vizsla"));
        dogBreeds.add(new DogBreed("n02096177_" , "Cairn"));
        dogBreeds.add(new DogBreed("n02113799_" , "Poodle"));
        dogBreeds.add(new DogBreed("n02106030_" , "Collie"));
        //When DogData object is created setImages() function is called.
        setImages();
    }

    //This method is used to set up the breed image to the DogBreed object.
    //The only short coming to this method is all dog breeds should have the same amount of images.
    private void setImages(){
        //Loops though every breed of dogs and set up the images for that breed
        for(DogBreed dogBreed : dogBreeds){
            ArrayList<String> images= new ArrayList<>();
            for( int i = 1 ; i <= 20 ; i++ ){
                String image = dogBreed.getBreedId() + i;
                images.add(image);
            }
            dogBreed.setImageUrls(images);
        }
    }
    public ArrayList<DogBreed> getDogBreeds() {
        return dogBreeds;
    }
}
