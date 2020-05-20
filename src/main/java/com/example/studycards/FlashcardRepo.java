package com.example.studycards;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FlashcardRepo {
    private FlashcardDao flashcardDao;
    private LiveData<List<Flashcard>> allFlashcards;

    public FlashcardRepo(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        flashcardDao = database.flashcardDao();
        allFlashcards = (LiveData<List<Flashcard>>) flashcardDao.getAll();
    }

    public void insert(Flashcard flashcard) {
        new InsertFlashcardAsyncTask(flashcardDao).execute(flashcard);
    }

    public void delete(Flashcard flashcard) {
        new DeleteFlashcardAsyncTask(flashcardDao).execute(flashcard);

    }

    public void update(Flashcard flashcard) {
        new UpdateFlashcardAsyncTask(flashcardDao).execute(flashcard);

    }

    public LiveData<List<Flashcard>> getAllFlashcards(){
        return allFlashcards;
    }

    private static class InsertFlashcardAsyncTask extends AsyncTask<Flashcard, Void, Void> {
        private FlashcardDao flashcardDao;

        private InsertFlashcardAsyncTask(FlashcardDao flashcardDao) {
            this.flashcardDao = flashcardDao;
        }

        @Override
        protected Void doInBackground(Flashcard... flashcards) {
            flashcardDao.insertAll(flashcards[0]);
            return null;
        }
    }

    private static class UpdateFlashcardAsyncTask extends AsyncTask<Flashcard, Void, Void> {
        private FlashcardDao flashcardDao;

        private UpdateFlashcardAsyncTask(FlashcardDao flashcardDao) {
            this.flashcardDao = flashcardDao;
        }

        @Override
        protected Void doInBackground(Flashcard... flashcards) {
            flashcardDao.update(flashcards[0]);
            return null;
        }
    }

    private static class DeleteFlashcardAsyncTask extends AsyncTask<Flashcard, Void, Void> {
        private FlashcardDao flashcardDao;

        private DeleteFlashcardAsyncTask(FlashcardDao flashcardDao) {
            this.flashcardDao = flashcardDao;
        }

        @Override
        protected Void doInBackground(Flashcard... flashcards) {
            flashcardDao.delete(flashcards[0]);
            return null;
        }
    }

}
