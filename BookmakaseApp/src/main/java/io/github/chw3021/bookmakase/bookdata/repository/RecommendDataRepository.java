package io.github.chw3021.bookmakase.bookdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.chw3021.bookmakase.bookdata.domain.RecommendData;

public interface RecommendDataRepository extends JpaRepository<RecommendData,Long>{
	Boolean existsByQueryAndCategoryId(String query, int categoryId);

	List<RecommendData> findAllByQuery(String query);
}
