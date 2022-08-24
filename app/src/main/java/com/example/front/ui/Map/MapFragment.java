package com.example.front.ui.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.retrofit.Cords;
import com.example.front.retrofit.MapObject;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.retrofit.maper.MapObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.LinearRing;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polygon;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.PolygonMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        ArrayList<Point> points= new ArrayList<>();
        points.add(new Point(55.5419811907339,47.51832077495023));
        points.add(new Point(55.52269541067465,47.53205151000539));
        points.add(new Point(55.52930651867314,47.55072531806098));
        points.add(new Point(55.53537221009383,47.56775142114878));
        points.add(new Point(55.54711210254822,47.55800259087905));

        Call<JsonObject> getObject = RetrofitClient.getInstance().getApi().getMapObject();
        getObject.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code()==200){
                    try {
                        JSONObject jsonObject= new JSONObject(new Gson().toJson(response.body()));
                        JSONArray objArray= jsonObject.getJSONArray("data");
                        DataData.MAP_OBJECTS.clear();
                        for (int i = 0; i < objArray.length(); i++) {
                            MapObject mapObject = new MapObject();
                            mapObject= MapObjectMapper.MapObjectFromJson(objArray.getJSONObject(i));
                            DataData.MAP_OBJECTS.add(mapObject);
                            addPolygon(DataData.MAP_OBJECTS.get(i),12,0.5f);
                            Log.d(CONST.SERVER_LOG,mapObject.toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


        return view;
    }


    private void addPlacemark(double latitude, double longitude){

        PlacemarkMapObject mapObject= mapObjects.addPlacemark(new Point(latitude,longitude),ImageProvider.fromResource(getContext(),R.drawable.ic_baseline_location_on_24));


    }


    private void addPolygon(MapObject pointList, int strokeColor, float strokeWidth){
        mapObjects = mapview.getMap().getMapObjects().addCollection();
        ArrayList<Cords> cords=pointList.getCoords();
        ArrayList<Point> points=new ArrayList<>();
        for (int j = 0; j < cords.size() ; j++) {
            points.add(new Point(cords.get(j).getLat(),cords.get(j).getLng()));
        }

        PolygonMapObject triangle = mapObjects.addPolygon(new Polygon(new LinearRing(points), new ArrayList<LinearRing>()));
        triangle.setFillColor(Integer.valueOf(strokeColor));
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