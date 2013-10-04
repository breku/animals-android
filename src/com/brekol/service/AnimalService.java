package com.brekol.service;

import com.brekol.model.shape.Animal;
import org.andengine.entity.IEntity;
import org.andengine.util.debug.Debug;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: Breku
 * Date: 04.10.13
 */
public class AnimalService {


    public List<Animal> getCurrentAnimals(Collection<IEntity> mChildren) {
        List<Animal> result = new ArrayList<Animal>();
        for (IEntity child : mChildren) {
            if (child instanceof Animal) {
                result.add((Animal) child);
            }
        }
        return result;
    }

    public Animal getClickedAnimal(List<Animal> animalList) {
        for (Animal animal : animalList) {
            if (animal.isClicked()) {
                return animal;
            }
        }
        return null;
    }

    public Animal getPlayedAnimal(List<Animal> animalList) {
        for (Animal animal : animalList) {
            if (animal.isSoundPlayed()) {
                return animal;
            }
        }
        return null;
    }


    public void playAnimalSound(List<Animal> animalList, Integer animalID) {
        animalList.get(animalID).playMusic();
    }

    public void good() {
        Debug.e("GOOD");
    }

    public void fail() {
        Debug.e("FAIL");

    }
}