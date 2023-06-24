package com.example.bookAPI.dto.Enum;

import lombok.Getter;

@Getter
public enum CategoryType {
    SELF_IMPROVEMENT(1, "자기계발"),
    POLITICS_SOCIETY(2, "정치/사회"),
    COMPUTER_IT(3, "컴퓨터/IT"),
    EMPLOYMENT_EXAMS(4, "취업/수험서"),
    TECHNOLOGY_ENGINEERING(5, "기술/공학"),
    HUMANITIES(6, "인문"),
    MAGINE(7, "잡지"),
    POETRY_ESSAY(8, "시/에세이"),
    HISTORY_CULTURE(9, "역사/문화"),
    FAMILY_PARENTING(10, "가정/육아"),
    HEALTH(11, "건강"),
    ART_POP_CULTURE(12, "예술/대중문화"),
    TRAVEL(13, "여행"),
    FOREIGN_LANGUAGE(14, "외국어"),
    SCIENCE(15, "과학"),
    IT_PROGRAMMING(16, "IT/프로그래밍"),
    CHILDREN_PRIMARY(17, "어린이(초등)"),
    ECONOMICS_MANAGEMENT(18, "경제경영"),
    HOBBY_PRACTICAL_SPORTS(19, "취미/실용/스포츠"),
    NOVEL(20, "소설"),
    TEENAGER(21, "청소년"),
    COMICS(22, "만화"),
    ART_ARCHITECTURE(23, "예술/건축");

    private final int id;
    private final String name;

    CategoryType(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
