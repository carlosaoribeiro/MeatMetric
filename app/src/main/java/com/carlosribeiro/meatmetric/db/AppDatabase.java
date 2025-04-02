package com.carlosribeiro.meatmetric.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import androidx.annotation.NonNull;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;


import com.carlosribeiro.meatmetric.data.UsuarioDao; // ✅ Agora está certo!
import com.carlosribeiro.meatmetric.model.Usuario;

@Database(entities = {Usuario.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UsuarioDao usuarioDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "meatmetric_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadExecutor().execute(() -> {
                                // Inserção automática do usuário de teste
                                UsuarioDao dao = INSTANCE.usuarioDao();
                                dao.inserirUsuario(new Usuario("teste@meatmetric.com", "senha123"));
                            });
                        }
                    })
                    .build();
        }
        return INSTANCE;
    }
}
