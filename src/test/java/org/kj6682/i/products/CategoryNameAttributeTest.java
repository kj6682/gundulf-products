package org.kj6682.i.products;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Function<T, R> myFunction = new Function<T, R>() {
 * public R apply(T t) {
 * return null;
 * }
 * };
 */
public class CategoryNameAttributeTest {
    Function<Object[], ListController.QueryAttributes.CategoryName> toCategoryName = new Function<Object[], ListController.QueryAttributes.CategoryName>() {
        public ListController.QueryAttributes.CategoryName apply(Object[] t) {
            return new ListController.QueryAttributes.CategoryName(t[0].toString(), t[1].toString());
        }
    };

    @Test
    public void convertSingleObject() {

        Object[] o = {"blue","Baba"};

        ListController.QueryAttributes.CategoryName n = toCategoryName.apply(o);
        Assert.assertEquals(o[0].toString(), n.getCategory());
        Assert.assertEquals(o[1].toString(), n.getName());

    }

    @Test
    public void convertListOfObjects() {

        Object[] o1 = {"blue", "Baba"};
        Object[] o2 = {"blue","Baba whisky"};
        Object[] o3 = {"blue","Baba limoncello"};

        List<Object[]> objects = new LinkedList<>();
        objects.add(o1);
        objects.add(o2);
        objects.add(o3);

        List<ListController.QueryAttributes.CategoryName> names = objects.stream()
                .map(toCategoryName)
                .collect(Collectors.<ListController.QueryAttributes.CategoryName>toList());

        for (int i = 0 ; i < names.size(); i++) {
            Assert.assertEquals(objects.get(i)[1].toString(), names.get(i).getName());
        }
    }


}//:)
