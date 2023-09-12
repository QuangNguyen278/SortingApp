package com.example.sortingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Controller
public class SortingController {
    private List<String> array;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("array", array);
        return "index";
    }

    @PostMapping("/generate")
    public String generateArray(Model model) {
        array = generateRandomArray();
        return "redirect:/";
    }
    @PostMapping("/customArray")
    public String createCustomArray(@RequestParam("elements") String elements, Model model) {
        array = Arrays.asList(elements.split(","));
        return "redirect:/";
    }
    @PostMapping("/sort/bubble")
    public String bubbleSort(Model model) {
        bubbleSortArray();
        model.addAttribute("array", array);
        return "redirect:/";
    }

    @PostMapping("/sort/selection")
    public String selectionSort(Model model) {
        selectionSortArray();
        model.addAttribute("array", array);
        return "redirect:/";
    }

    @PostMapping("/sort/insertion")
    public String insertionSort(Model model) {
        insertionSortArray();
        model.addAttribute("array", array);
        return "redirect:/";
    }

    @PostMapping("/sort/merge")
    public String mergeSort(Model model) {
        mergeSortArray();
        model.addAttribute("array", array);
        return "redirect:/";
    }

    @PostMapping("/sort/quick")
    public String quickSort(Model model) {
        quickSortArray();
        model.addAttribute("array", array);
        return "redirect:/";
    }

    private List<String> generateRandomArray() {
        List<String> randomArray = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            StringBuilder builder = new StringBuilder();
            int length = random.nextInt(5) + 1;

            for (int j = 0; j < length; j++) {
                int randomChar = random.nextInt(26);
                char c = (char) ('a' + randomChar);
                builder.append(c);
            }

            randomArray.add(builder.toString());
        }

        return randomArray;
    }

    private void bubbleSortArray() {
        Collections.sort(array);
    }

    private void selectionSortArray() {
        for (int i = 0; i < array.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.size(); j++) {
                if (array.get(j).compareTo(array.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            Collections.swap(array, i, minIndex);
        }
    }

    private void insertionSortArray() {
        for (int i = 1; i < array.size(); i++) {
            String key = array.get(i);
            int j = i - 1;
            while (j >= 0 && array.get(j).compareTo(key) > 0) {
                array.set(j + 1, array.get(j));
                j--;
            }
            array.set(j + 1, key);
        }
    }

    private void mergeSortArray() {
        mergeSortHelper(0, array.size() - 1);
    }

    private void mergeSortHelper(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortHelper(left, mid);
            mergeSortHelper(mid + 1, right);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        List<String> tempArray = new ArrayList<>(Arrays.asList(new String[right - left + 1]));

        int i = left;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= right) {
            if (array.get(i).compareTo(array.get(j)) <= 0) {
                tempArray.set(k, array.get(i));
                i++;
            } else {
                tempArray.set(k, array.get(j));
                j++;
            }
            k++;
        }

        while (i <= mid) {
            tempArray.set(k, array.get(i));
            i++;
            k++;
        }

        while (j <= right) {
            tempArray.set(k, array.get(j));
            j++;
            k++;
        }

        for (int m = 0; m < tempArray.size(); m++) {
            array.set(left + m, tempArray.get(m));
        }
    }

    private void quickSortArray() {
        quickSortHelper(0, array.size() - 1);
    }

    private void quickSortHelper(int left, int right) {
        if (left < right) {
            int pivotIndex = partition(left, right);
            quickSortHelper(left, pivotIndex - 1);
            quickSortHelper(pivotIndex + 1, right);
        }
    }

    private int partition(int left, int right) {
        String pivot = array.get(right);
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (array.get(j).compareTo(pivot) < 0) {
                i++;
                Collections.swap(array, i, j);
            }
        }

        Collections.swap(array, i + 1, right);
        return i + 1;
    }
}