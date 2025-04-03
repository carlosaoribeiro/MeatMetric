package com.carlosribeiro.meatmetric.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.carlosribeiro.meatmetric.data.UsuarioDao;
import com.carlosribeiro.meatmetric.model.Usuario;

import java.util.concurrent.Executors;

@Database(entities = {Usuario.class}, version = 2)
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

                            // Usa a própria instância criada para inserir o usuário de teste
                            Executors.newSingleThreadExecutor().execute(() -> {
                                AppDatabase dbInstance = INSTANCE;
                                if (dbInstance != null) {
                                    UsuarioDao dao = dbInstance.usuarioDao();
                                    dao.inserirUsuario(new Usuario(
                                            "Usuário Teste",
                                            "teste@meatmetric.com",
                                            "senha123"
                                    ));
                                }
                            });
                        }
                    })
                    .build();
        }
        return INSTANCE;
    }
}
