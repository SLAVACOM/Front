package com.example.front.ui.Map;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.retrofit.Cords;
import com.example.front.retrofit.ListRESPONSE;
import com.example.front.retrofit.MapObject;
import com.example.front.retrofit.RetrofitClient;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.LinearRing;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polygon;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.PolygonMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapFragment extends Fragment {
    private MapView mapview;
    public static final double lat = 55.535667;
    public static final double lon = 47.504093;
    public static final double OBJECT_SIZE =0.001;

    private MapObjectCollection mapObjects;


    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map,container,false);
        mapview = view.findViewById(R.id.mapview);
        mapview.getMap().move(
                new CameraPosition(new Point(55.535667, 47.504093), 13.5f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        Call<ListRESPONSE<MapObject>> getObject = RetrofitClient.getInstance().getApi().getMapObject();
        getObject.enqueue(new Callback<ListRESPONSE<MapObject>>() {
            @Override
            public void onResponse(Call<ListRESPONSE<MapObject>> call, Response<ListRESPONSE<MapObject>> response) {
                if (response.code()==200){
                    List<MapObject> data = response.body().getData();
                    Log.d(CONST.SERVER_LOG, data.toString());
                    for (MapObject mo: data) {
                        addPolygon(mo);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListRESPONSE<MapObject>> call, Throwable t) {
                t.printStackTrace();
            }
        });


        return view;
    }


    private void addPlacemark(double latitude, double longitude){

        PlacemarkMapObject mapObject= mapObjects.addPlacemark(new Point(latitude,longitude),ImageProvider.fromResource(getContext(),R.drawable.ic_baseline_location_on_24));


    }


    private void addPolygon(MapObject mapObject){
        mapObjects = mapview.getMap().getMapObjects().addCollection();
        ArrayList<ArrayList<Cords>> cords=mapObject.getCoords();
        ArrayList<Point> points=new ArrayList<>();
        for (int i = 0; i < cords.size() ;i++) {
            ArrayList<Cords> item = cords.get(i);
            for (int j = 0; j < item.size(); j++) {
                points.add(new Point(item.get(j).getLat(), item.get(j).getLng()));
            }
        }

        PolygonMapObject polygon = mapObjects.addPolygon(new Polygon(new LinearRing(points), new ArrayList<LinearRing>()));
        mapObject.setPolygonMapObject(polygon);
        polygon.setUserData(mapObject);
        String color = mapObject.getColor();
        if (color.length() == 9) {
            color = "#" + color.substring(7,9)+color.substring(1,7);
        }
        int intcolor = Color.parseColor(color);
        polygon.setFillColor(intcolor);
        polygon.addTapListener((mapObject1, point) -> {
            MapObject mo = (MapObject) mapObject1.getUserData();
            Toast toast = Toast.makeText(
                    getActivity().getBaseContext(),
                    mo.getName() + (mo.getPoints() > 0 ? " (для посещения нужно " + mo.getPoints() + " баллов)" : ""),
                    Toast.LENGTH_SHORT);
            toast.show();
            return true;
        });
    }




    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapview.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        MapKitFactory.getInstance().onStop();
        mapview.onStop();
    }
}