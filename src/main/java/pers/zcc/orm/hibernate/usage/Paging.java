package pers.zcc.orm.hibernate.usage;

import io.quarkus.panache.common.Page;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;

import java.util.List;
/**
 * Paging
 *
 * @author zhangchangchun
 * @date 2024/1/15
 * @since 1.0
 */
public class Paging {

//    public List<Person> pageedQuery(){
//        // create a query for all living persons
//        PanacheQuery<Person> livingPersons = Person.find("status", Status.Alive);
//
//        // make it use pages of 25 entries at a time
//        livingPersons.page(Page.ofSize(25));
//
//        // get the first page
//        List<Person> firstPage = livingPersons.list();
//
//        // get the second page
//        List<Person> secondPage = livingPersons.nextPage().list();
//
//        // get page 7
//        List<Person> page7 = livingPersons.page(Page.of(7, 25)).list();
//
//        // get the number of pages
//        int numberOfPages = livingPersons.pageCount();
//
//        // get the total number of entities returned by this query without paging
//        long count = livingPersons.count();
//
//        // and you can chain methods of course
//        return Person.find("status",Status.Alive).page(Page.ofSize(25)).nextPage().stream()...;
//    }

    //范围查询与分页查询不能混用
//    public void rangeQuery(){
//        // create a query for all living persons
//        PanacheQuery<Person> livingPersons = Person.find("status", Status.Alive);
//
//        // make it use a range: start at index 0 until index 24 (inclusive).
//        livingPersons.range(0, 24);
//
//        // get the range
//        List<Person> firstRange = livingPersons.list();
//
//        // to get the next range, you need to call range again
//        List<Person> secondRange = livingPersons.range(25, 49).list();

//    }

//    public void sort(){
//        List<Person> persons = Person.list("order by name,birth");
//        List<Person> persons = Person.list(Sort.by("name").and("birth"));
//
//        // and with more restrictions
//        List<Person> persons = Person.list("status", Sort.by("name").and("birth"), Status.Alive);
//
//        // and list first the entries with null values in the field "birth"
//        List<Person> persons = Person.list(Sort.by("birth", Sort.NullPrecedence.NULLS_FIRST));
//
//    }
}
