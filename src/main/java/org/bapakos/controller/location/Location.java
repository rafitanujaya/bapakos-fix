package org.bapakos.controller.location;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class Location {
    public static ObservableList<String> getProvinsiJawa() {
        return FXCollections.observableArrayList(
                "Banten", "DKI Jakarta", "Jawa Barat", "Jawa Tengah", "DI Yogyakarta", "Jawa Timur"
        );
    }

    public static Map<String, ObservableList<String>> getKabupatenPerProvinsi() {
        Map<String, ObservableList<String>> data = new HashMap<>();

        data.put("Banten", FXCollections.observableArrayList(
                "Kabupaten Pandeglang", "Kabupaten Lebak", "Kabupaten Tangerang", "Kabupaten Serang",
                "Kota Tangerang", "Kota Cilegon", "Kota Serang", "Kota Tangerang Selatan"
        ));

        data.put("DKI Jakarta", FXCollections.observableArrayList(
                "Kabupaten Kepulauan Seribu", "Kota Jakarta Barat", "Kota Jakarta Timur",
                "Kota Jakarta Selatan", "Kota Jakarta Utara", "Kota Jakarta Pusat"
        ));

        data.put("Jawa Barat", FXCollections.observableArrayList(
                "Kabupaten Bandung", "Kabupaten Bandung Barat",
                "Kabupaten Bekasi", "Kabupaten Bogor", "Kabupaten Ciamis", "Kabupaten Cianjur", "Kabupaten Cirebon",
                "Kabupaten Garut", "Kabupaten Indramayu", "Kabupaten Karawang", "Kabupaten Kuningan", "Kabupaten Majalengka",
                "Kabupaten Pangandaran", "Kabupaten Purwakarta", "Kabupaten Subang", "Kabupaten Sukabumi", "Kabupaten Sumedang",
                "Kabupaten Tasikmalaya", "Kota Bandung", "Kota Banjar", "Kota Bekasi", "Kota Bogor",
                "Kota Cimahi", "Kota Cirebon", "Kota Depok", "Kota Sukabumi", "Kota Tasikmalaya"
        ));

        data.put("Jawa Tengah", FXCollections.observableArrayList(
                "Kabupaten Banjarnegara", "Kabupaten Banyumas", "Kabupaten Batang", "Kabupaten Blora", "Kabupaten Boyolali",
                "Kabupaten Brebes", "Kabupaten Cilacap", "Kabupaten Demak", "Kabupaten Grobogan", "Kabupaten Jepara",
                "Kabupaten Karanganyar", "Kabupaten Kebumen", "Kabupaten Kendal", "Kabupaten Klaten", "Kabupaten Kudus",
                "Kabupaten Magelang", "Kabupaten Pati", "Kabupaten Pekalongan", "Kabupaten Pemalang", "Kabupaten Purbalingga",
                "Kabupaten Purworejo", "Kabupaten Rembang", "Kabupaten Semarang", "Kabupaten Sragen", "Kabupaten Sukoharjo",
                "Kabupaten Tegal", "Kabupaten Temanggung", "Kabupaten Wonogiri", "Kabupaten Wonosobo", "Kota Magelang",
                "Kota Pekalongan", "Kota Salatiga", "Kota Semarang", "Kota Surakarta", "Kota Tegal"
        ));

        data.put("DI Yogyakarta", FXCollections.observableArrayList(
                "Kabupaten Bantul", "Kabupaten Gunungkidul", "Kabupaten Kulon Progo", "Kabupaten Sleman", "Kota Yogyakarta"
        ));

        data.put("Jawa Timur", FXCollections.observableArrayList(
                "Kabupaten Bangkalan", "Kabupaten Banyuwangi", "Kabupaten Blitar",
                "Kabupaten Bojonegoro", "Kabupaten Bondowoso", "Kabupaten Gresik", "Kabupaten Jember", "Kabupaten Jombang",
                "Kabupaten Kediri", "Kabupaten Lamongan", "Kabupaten Lumajang", "Kabupaten Madiun", "Kabupaten Magetan",
                "Kabupaten Malang", "Kabupaten Mojokerto", "Kabupaten Nganjuk", "Kabupaten Ngawi", "Kabupaten Pacitan",
                "Kabupaten Pamekasan", "Kabupaten Pasuruan", "Kabupaten Ponorogo", "Kabupaten Probolinggo", "Kabupaten Sampang",
                "Kabupaten Sidoarjo", "Kabupaten Situbondo", "Kabupaten Sumenep", "Kabupaten Trenggalek", "Kabupaten Tuban",
                "Kabupaten Tulungagung", "Kota Batu", "Kota Blitar", "Kota Kediri", "Kota Madiun",
                "Kota Malang", "Kota Mojokerto", "Kota Pasuruan","Kota Probolinggo", "Kota Surabaya"
        ));

        return data;
    }
}
