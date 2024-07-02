package com.hanaro.hanafun.lesson.domain;

import com.hanaro.hanafun.host.domain.HostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    List<LessonEntity> findLessonEntitiesByHostEntity(HostEntity hostEntity);
    Optional<List<LessonEntity>> findByHostEntityHostId(Long hostId);
    Optional<List<LessonEntity>> findByCategoryEntityCategoryId(Long categoryId);

    @Query("SELECT L FROM LessonEntity L WHERE L.title Like %:query% OR L.description Like %:query%")
    List<LessonEntity> findBySearchLessonAll(String query);
    @Query(value = "SELECT L FROM LessonEntity L WHERE L.categoryEntity.categoryId = :categoryId AND (L.title Like %:query% OR L.description Like %:query%)")
    List<LessonEntity> findBySearchLessonCategory(Long categoryId, String query);

    @Query(value = "SELECT L FROM LessonEntity L WHERE L.title Like %:query% OR L.description Like %:query% ORDER BY L.createdDate DESC")
    List<LessonEntity> findSearchFilterLessonAllByOrderByDate(String query);
    @Query(value = "SELECT L FROM LessonEntity L WHERE L.title Like %:query% OR L.description Like %:query% ORDER BY L.applicantSum DESC")
    List<LessonEntity> findSearchFilterLessonAllByOrderByApplicantSum(String query);
    @Query(value = "SELECT L FROM LessonEntity L WHERE L.title Like %:query% OR L.description Like %:query% ORDER BY L.price ASC")
    List<LessonEntity> findSearchFilterLessonAllByOrderByPriceAsc(String query);
    @Query(value = "SELECT L FROM LessonEntity L WHERE L.title Like %:query% OR L.description Like %:query% ORDER BY L.price DESC")
    List<LessonEntity> findSearchFilterLessonAllByOrderByPriceDesc(String query);

    @Query(value = "SELECT L FROM LessonEntity L WHERE L.categoryEntity.categoryId = :categoryId AND (L.title Like %:query% OR L.description Like %:query%) ORDER BY L.createdDate DESC")
    List<LessonEntity> findSearchFilterLessonCategoryByOrderByDate(Long categoryId, String query);
    @Query(value = "SELECT L FROM LessonEntity L WHERE L.categoryEntity.categoryId = :categoryId AND (L.title Like %:query% OR L.description Like %:query%) ORDER BY L.applicantSum DESC")
    List<LessonEntity> findSearchFilterLessonCategoryByOrderByApplicantSum(Long categoryId, String query);
    @Query(value = "SELECT L FROM LessonEntity L WHERE L.categoryEntity.categoryId = :categoryId AND (L.title Like %:query% OR L.description Like %:query%) ORDER BY L.price ASC")
    List<LessonEntity> findSearchFilterLessonCategoryByOrderByPriceAsc(Long categoryId, String query);
    @Query(value = "SELECT L FROM LessonEntity L WHERE L.categoryEntity.categoryId = :categoryId AND (L.title Like %:query% OR L.description Like %:query%) ORDER BY L.price DESC")
    List<LessonEntity> findSearchFilterLessonCategoryByOrderByPriceDesc(Long categoryId, String query);
}
