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
public class NameAttributeTest {
    Function<Object, ListController.QueryAttributes.Name> toName = new Function<Object, ListController.QueryAttributes.Name>() {
        public ListController.QueryAttributes.Name apply(Object t) {
            return new ListController.QueryAttributes.Name(t.toString());
        }
    };

    @Test
    public void convertSingleObject() {

        Object o = "Baba";

        ListController.QueryAttributes.Name n = toName.apply(o);
        Assert.assertEquals(o.toString(), n.getName());

    }

    @Test
    public void convertListOfObjects() {

        Object o1 = "Baba";
        Object o2 = "Baba whisky";
        Object o3 = "Baba limoncello";

        List<Object> objects = new LinkedList<>();
        objects.add(o1);
        objects.add(o2);
        objects.add(o3);

        List<ListController.QueryAttributes.Name> names = objects.stream()
                .map(toName)
                .collect(Collectors.<ListController.QueryAttributes.Name>toList());

        for (int i = 0 ; i < names.size(); i++) {
            Assert.assertEquals(objects.get(i).toString(), names.get(i).getName());
        }
    }


}//:)
