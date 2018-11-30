package com.example.digital.borradorproyectointegrador.dao;

import com.example.digital.borradorproyectointegrador.R;
import com.example.digital.borradorproyectointegrador.model.comentario.Comentario;

import java.util.ArrayList;
import java.util.List;

public class DAOComentario {


    public List<Comentario> dameComentarios(){
        List<Comentario> comentarioList = new ArrayList<>();

        comentarioList.add(new Comentario("Cars",R.drawable.cars,R.drawable.shadow_profile,"PEPITO",2,"Es una pilicula que trata de autos que cobran vida en un mundo surrealista manejado por el patriarcado, en donde le ensenan a los chicos a maltratar a las mujeres"));
        comentarioList.add(new Comentario("Coco",R.drawable.coco,R.drawable.shadow_profile,"Pirulito",5,"Encantadora, para ver con toda la familia y unos panuelos cerca. No se la pierdan!"));
        comentarioList.add(new Comentario("Star Wars",R.drawable.starwars,R.drawable.shadow_profile,"Fulanito",1,"UN DESASTRE!! Desde que se vendieron a Disney todo es un desastre, mi vida ya no tiene sentido"));
        comentarioList.add(new Comentario("Tomorrowland",R.drawable.tomorrowland,R.drawable.shadow_profile,"Cosme",4,"Excelente, me encanto! Muy recomendable"));
        comentarioList.add(new Comentario("Cars",R.drawable.cars,R.drawable.shadow_profile,"PEPITO",2,"Es una pilicula que trata de autos que cobran vida en un mundo surrealista manejado por el patriarcado, en donde le ensenan a los chicos a maltratar a las mujeres"));
        comentarioList.add(new Comentario("Coco",R.drawable.coco,R.drawable.shadow_profile,"Pirulito",5,"Encantadora, para ver con toda la familia y unos panuelos cerca. No se la pierdan!"));
        comentarioList.add(new Comentario("Star Wars",R.drawable.starwars,R.drawable.shadow_profile,"Fulanito",1,"UN DESASTRE!! Desde que se vendieron a Disney todo es un desastre, mi vida ya no tiene sentido"));
        comentarioList.add(new Comentario("Tomorrowland",R.drawable.tomorrowland,R.drawable.shadow_profile,"Cosme",4,"Excelente, me encanto! Muy recomendable"));
        comentarioList.add(new Comentario("Cars",R.drawable.cars,R.drawable.shadow_profile,"PEPITO",2,"Es una pilicula que trata de autos que cobran vida en un mundo surrealista manejado por el patriarcado, en donde le ensenan a los chicos a maltratar a las mujeres"));
        comentarioList.add(new Comentario("Coco",R.drawable.coco,R.drawable.shadow_profile,"Pirulito",5,"Encantadora, para ver con toda la familia y unos panuelos cerca. No se la pierdan!"));
        comentarioList.add(new Comentario("Star Wars",R.drawable.starwars,R.drawable.shadow_profile,"Fulanito",1,"UN DESASTRE!! Desde que se vendieron a Disney todo es un desastre, mi vida ya no tiene sentido"));
        comentarioList.add(new Comentario("Tomorrowland",R.drawable.tomorrowland,R.drawable.shadow_profile,"Cosme",4,"Excelente, me encanto! Muy recomendable"));
        comentarioList.add(new Comentario("Cars",R.drawable.cars,R.drawable.shadow_profile,"PEPITO",2,"Es una pilicula que trata de autos que cobran vida en un mundo surrealista manejado por el patriarcado, en donde le ensenan a los chicos a maltratar a las mujeres"));
        comentarioList.add(new Comentario("Coco",R.drawable.coco,R.drawable.shadow_profile,"Pirulito",5,"Encantadora, para ver con toda la familia y unos panuelos cerca. No se la pierdan!"));
        comentarioList.add(new Comentario("Star Wars",R.drawable.starwars,R.drawable.shadow_profile,"Fulanito",1,"UN DESASTRE!! Desde que se vendieron a Disney todo es un desastre, mi vida ya no tiene sentido"));
        comentarioList.add(new Comentario("Tomorrowland",R.drawable.tomorrowland,R.drawable.shadow_profile,"Cosme",4,"Excelente, me encanto! Muy recomendable"));

        return comentarioList;
    }

}
