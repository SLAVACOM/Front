package com.example.front.ui.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.retrofit.Data;
import com.example.front.retrofit.MapObject;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.LinearRing;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polygon;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkAnimation;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.PolygonMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.util.ArrayList;
import java.util.List;


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
        ArrayList<Point> points= new ArrayList<>();
        points.add(new Point(55.5419811907339,47.51832077495023));
        points.add(new Point(55.52269541067465,47.53205151000539));
        points.add(new Point(55.52930651867314,47.55072531806098));
        points.add(new Point(55.53537221009383,47.56775142114878));
        points.add(new Point(55.54711210254822,47.55800259087905));

        for (int i = 0; i < DataData.MAP_OBJECTS.size(); i++) {
            Log.d(CONST.SERVER_LOG, ""+DataData.MAP_OBJECTS.get(i));
            MapObject mapObject = DataData.MAP_OBJECTS.get(i);
            ArrayList<Point> Points = new ArrayList<>();
            if (mapObject.getType().equals("polygon")){
                for (int j = 0; j < mapObject.getCoords().size(); j++) {
                    Log.d(CONST.SERVER_LOG,""+mapObject.getCoords().get(i).getLat());
                    Points.add(new Point(mapObject.getCoords().get(i).getLat(),mapObject.getCoords().get(i).getLng()));
                }
                mapObjects = mapview.getMap().getMapObjects().addCollection();
                PolygonMapObject triangle = mapObjects.addPolygon(
                        new Polygon(new LinearRing(Points), new ArrayList<LinearRing>()));
                triangle.setFillColor(-1212);
            }
        }





        return view;
    }


    private void addPlacemark(double latitude, double longitude){

        PlacemarkMapObject mapObject= mapObjects.addPlacemark(new Point(latitude,longitude),ImageProvider.fromResource(getContext(),R.drawable.ic_baseline_location_on_24));


    }


    private void addPolygon(ArrayList<Point> pointList, String strokeColor,float strokeWidth){
        ArrayList<Point> Points = pointList;

        mapObjects = mapview.getMap().getMapObjects().addCollection();

        PolygonMapObject triangle = mapObjects.addPolygon(
                new Polygon(new LinearRing(Points), new ArrayList<LinearRing>()));
        triangle.setFillColor(Integer.valueOf(strokeColor));

        triangle.setStrokeWidth(strokeWidth);
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