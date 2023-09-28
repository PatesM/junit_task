package exercise.article;

import exercise.worker.WorkerImpl;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@Log
@DisplayName("Тестирование реализации класса WorkerImpl")
class WorkerImplTest {

    private WorkerImpl worker;

    @Mock
    private Library library;

    static Stream<Arguments> getCatalog_argsProviderFactory() {
        return Stream.of(
                Arguments.of(
                        List.of(),
                        "Список доступных статей:\n",
                        "1.1 Получение списка названий статей из пустой библиотеки"
                ),
                Arguments.of(
                        List.of(
                                "7 мифов об IT",
                                "Как правильно изучать языки программирования",
                                "Как составить резюме тестировщику",
                                "Почему важны soft skills?",
                                "Сколько времени нужно, чтобы выучить Java"
                        ),
                        """
                                Список доступных статей:
                                    7 мифов об IT
                                    Как правильно изучать языки программирования
                                    Как составить резюме тестировщику
                                    Почему важны soft skills?
                                    Сколько времени нужно, чтобы выучить Java
                                """,
                        "1.2 Получение списка названий статей из библиотеки c 5 статьями"
                )
        );
    }

    static Stream<Arguments> prepareArticlesMethod_argsProviderFactory() {
        return Stream.of(
                Arguments.of(
                        List.of(new Article(
                                null,
                                null,
                                null,
                                null)
                        ),
                        List.of(),
                        "2.1 Отбрасывание статьи с пустыми полями"
                ),
                Arguments.of(
                        List.of(new Article(null,
                                "Test content for test case 2",
                                "Author TC2",
                                LocalDate.of(2023, 2, 12))
                        ),
                        List.of(),
                        "2.2 Отбрасывание статьи с пустым полем title"
                ),
                Arguments.of(
                        List.of(new Article(
                                "Article title for test case 3",
                                null,
                                "Author TC3",
                                LocalDate.of(2023, 3, 23))
                        ),
                        List.of(),
                        "2.3 Отбрасывание статьи с пустым полем content"
                ),
                Arguments.of(
                        List.of(new Article(
                                "Article title for test case 4",
                                "Test content for test case 4",
                                null,
                                LocalDate.of(2022, 3, 3))
                        ),
                        List.of(),
                        "2.4 Отбрасывание статьи с пустым полем author"
                ),
                Arguments.of(
                        List.of(new Article(
                                "Article title for test case 5",
                                "Test content for test case 5",
                                "Author TC5",
                                null)
                        ),
                        List.of(new Article("Article title for test case 5",
                                "Test content for test case 5",
                                "Author TC5",
                                LocalDate.now())
                        ),
                        "2.5 Подготовка статьи с пустым полем creationDate"
                ),
                Arguments.of(
                        List.of(new Article(
                                "Article title for test case 6",
                                "Test content for test case 6",
                                "Author TC6",
                                LocalDate.of(2023, 6, 16))
                        ),
                        List.of(new Article(
                                "Article title for test case 6",
                                "Test content for test case 6",
                                "Author TC6",
                                LocalDate.of(2023, 6, 16))
                        ),
                        "2.6 Подготовка статьи с заполненными полями"
                )
        );
    }

    static Stream<Arguments> NotAddNewArticles_argsProviderFactory() {
        return Stream.of(
                Arguments.of(
                        List.of(new Article(
                                null,
                                null,
                                null,
                                null)
                        ),
                        "3.1 Добавление статьи с пустыми полями"
                ),
                Arguments.of(
                        List.of(new Article(" ",
                                "Test content for test case 2",
                                "Author TC2",
                                LocalDate.of(2023, 2, 12))
                        ),
                        "3.2 Добавление статьи с пустым полем title"
                ),
                Arguments.of(
                        List.of(new Article(
                                "Article title for test case 3",
                                null,
                                "Author TC3",
                                LocalDate.of(2023, 3, 23))
                        ),
                        "3.3 Добавление статьи с пустым полем content"
                ),
                Arguments.of(
                        List.of(new Article(
                                "Article title for test case 4",
                                "Test content for test case 4",
                                null,
                                LocalDate.of(2022, 3, 3))
                        ),
                        "3.4 Добавление статьи с пустым полем author"
                )
        );
    }

    static Stream<Arguments> addNewArticles_argsProviderFactory() {
        return Stream.of(
                Arguments.of(
                        List.of(new Article(
                                "Article title for test case 5",
                                "Test content for test case 5",
                                "Author TC5",
                                null)
                        ),
                        "3.5 Добавление статьи с пустым полем creationDate"
                ),
                Arguments.of(
                        List.of(new Article(
                                        "Как составить резюме тестировщику",
                                        "Работодатель тратит всего 20 секунд на первичный просмотр резюме. Поэтому очень важно составить резюме Junior-тестировщика таким образом, чтобы hr-специалист сразу видел ваши ключевые навыки. О том, как это сделать, читайте в блоге Kata Academy.",
                                        "Сергей Сергеев",
                                        LocalDate.of(2023, 1, 18)),
                                new Article(
                                        "Как составить резюме тестировщику",
                                        "Работодатель тратит всего 20 секунд на первичный просмотр резюме. Поэтому очень важно составить резюме Junior-тестировщика таким образом, чтобы hr-специалист сразу видел ваши ключевые навыки. О том, как это сделать, читайте в блоге Kata Academy.",
                                        "Сергей Сергеев",
                                        LocalDate.of(2023, 1, 18)
                                )
                        ),
                        "3.6 Добавление статьи-дубликата"
                ),
                Arguments.of(
                        List.of(new Article(
                                "Article title for test case 7",
                                "Test content for test case 7",
                                "Author TC7",
                                LocalDate.of(2023, 7, 26))
                        ),
                        "3.7 Добавление статьи с заполненными полями, сортировка по годам и обновление каталога"
                )
        );
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        worker = new WorkerImpl(library);
    }

    @DisplayName("1. Тестирование метода getCatalog")
    @ParameterizedTest(name = "{2}")
    @MethodSource("getCatalog_argsProviderFactory")
    public void getCatalog_shouldGetListTitlesOfArticles(List<String> titles, String result, String name) {
        given(library.getAllTitles()).willReturn(titles);
        worker.getCatalog();
        verify(library).getAllTitles();
        assertEquals(result, worker.getCatalog());
        log.info("Тест " + "\"" + name + "\"" + " - успешно пройден.\n");
    }

    @DisplayName("2. Тестирование метода prepareArticles")
    @ParameterizedTest(name = "{2}")
    @MethodSource("prepareArticlesMethod_argsProviderFactory")
    public void prepareArticles_shouldDiscardArticleWithEmptyFields(List<Article> articles, List<Article> result,
                                                                    String name) {
        assertEquals(worker.prepareArticles(articles), result);
        log.info("Тест " + "\"" + name + "\"" + " - успешно пройден.\n");
    }

    @DisplayName("3. Тестирование метода addNewArticles")
    @ParameterizedTest(name = "{1}")
    @MethodSource("NotAddNewArticles_argsProviderFactory")
    public void addNewArticles_shouldNotAddArticleSortByYearUpdateCatalog(List<Article> articlesForAdding,
                                                                          String name) {
        worker.addNewArticles(articlesForAdding);
        verifyNoInteractions(library);
        log.info("Тест " + "\"" + name + "\"" + " - успешно пройден.\n");
    }

    @DisplayName("3. Тестирование метода addNewArticles")
    @ParameterizedTest(name = "{1}")
    @MethodSource("addNewArticles_argsProviderFactory")
    public void addNewArticles_shouldAddArticleSortByYearUpdateCatalog(List<Article> articlesForAdding, String name) {
        worker.addNewArticles(articlesForAdding);
        verify(library).store(any(Integer.class), any());
        verify(library).updateCatalog();
        log.info("Тест " + "\"" + name + "\"" + " - успешно пройден.\n");
    }
}
