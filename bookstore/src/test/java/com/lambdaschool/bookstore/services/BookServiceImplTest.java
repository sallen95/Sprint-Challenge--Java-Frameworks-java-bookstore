package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.BookstoreApplication;
import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.Author;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.models.Section;
import com.lambdaschool.bookstore.models.Wrote;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApplication.class)
//**********
// Note security is handled at the controller, hence we do not need to worry about security here!
//**********
public class BookServiceImplTest
{

    @Autowired
    private BookService bookService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private AuthorService authorService;

    @Before
    public void setUp() throws
            Exception
    {

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws
            Exception
    {
    }

    @Test
    public void findAll()
    {
        System.out.println(bookService.findAll());
        assertEquals(5,
            bookService.findAll().size());
    }

    @Test
    public void findBookById()
    {
        assertEquals("Flatterland",
            bookService.findBookById(26).getTitle());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void notFindBookById()
    {
        assertEquals("Flatterland",
            bookService.findBookById(50000).getTitle());
    }

    @Test
    public void delete()
    {
        bookService.delete(26);
        assertEquals(4,
            bookService.findAll().size());
    }

    @Test
    public void save()
    {
        Author testAuthor = new Author("Scott", "Allen");
        testAuthor = authorService.save(testAuthor);

        Section testSection = new Section("TestSection");
        testSection = sectionService.save(testSection);

        Book testBook = new Book("Test Book", "9780738206782", 2006, testSection);

        testBook.getWrotes()
            .add(new Wrote(testAuthor, new Book()));

        Book addTestBook = bookService.save(testBook);
        assertNotNull(addTestBook);
        assertEquals("Test Book",
            addTestBook.getTitle());
    }
}