package com.example.day1listsetmap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListInterface {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        // Khi sử dụng for each như sau sẽ   văng Exception in thread "main" java.util.ConcurrentModificationException
//        for (String s : list) {
//            if (s.equals("B")) {
//                list.remove(s);
//            }
//            System.out.println("Item: " + s);
//        }
        // Vòng for trên tương đương với:
        for (Iterator<String> it = list.iterator(); it.hasNext();) {
            String s = it.next();
            if(it.next().equals("B")) list.remove(s);
            // for-each cũng dùng Iterator, nhưng không thể điều khiển được iterator tạo trong for ()
            // Khi gọi list.remove(s), bạn đã thay đổi collection trực tiếp, không thông qua iterator
            // → khiến iterator bị mất đồng bộ (modCount thay đổi) → lỗi ConcurrentModificationException.
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (s.equals("B")) {
                it.remove(); // hợp lệ, không lỗi
            }
        }
        /*
        Khi gọi it.remove(), bản thân Iterator sẽ:
		Gọi ArrayList.remove() nội bộ.
		Cập nhật lại modCount của iterator sao cho khớp với collection.
		Nói ngắn gọn: Iterator.remove() là cách hợp lệ duy nhất để xóa phần tử trong khi đang duyệt.
         */

        // hợp lệ, không lỗi
        list.removeIf(s -> s.equals("C")); // Hàm removeIf bên trong cũng tạo Iterator riêng chứ không dùng trong vòng for
        /*
        default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    	}
         */

        System.out.println(list);
    }
}
