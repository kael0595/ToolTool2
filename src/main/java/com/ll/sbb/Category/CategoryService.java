//package com.ll.sbb.Category;
//
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class CategoryService {
//
//    @Autowired
//    private final CategoryRepository categoryRepository;
//
//
//    public List<Category> getMainCategories() {
//        return categoryRepository.findByMainCategoryIsNull();
//    }
//
//    public List<Category> getSubCategories(Long mainId) {
//        Category mainCategory = getCategoryById(mainId);
//        return categoryRepository.findByMainCategory(mainCategory);
//    }
//
//    public Category getCategoryById(Long id) {
//        return categoryRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Category not found"));
//    }
//
//
//}
