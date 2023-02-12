package com.example.FoodieGo.controller;

import com.example.FoodieGo.database.DBLoader;
import com.example.FoodieGo.database.RDBMS_TO_JSON;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MyController {

    @Autowired
    EmailSenderService sendEmail;

    @PostMapping(value = "/one", produces = "text/html")
    String adminLogin(@RequestParam String email, @RequestParam String password) {
        String result = email + "--> " + password;
        result += "fail";
        try {
            ResultSet rs = DBLoader.executeSQL_Query("select * from admin_login where admin_id='" + email + "' and password='" + password + "'");
            if (rs.next()) {
                result = "success";
            }
        } catch (Exception e) {
        }
        return result;
    }

    @PostMapping(value = "/two", produces = "text/html")
    String registerRestaurant(@RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam String description, @RequestParam String mobile, @RequestParam String address, @RequestParam String start, @RequestParam String end, @RequestParam MultipartFile photo, @RequestParam String latitude, @RequestParam String longitude, @RequestParam String city, @RequestParam String mindelivery, @RequestParam String accountno, @RequestParam MultipartFile fssai, @RequestParam MultipartFile aadhar, @RequestParam MultipartFile pan, @RequestParam MultipartFile menu, @RequestParam MultipartFile cheque, @RequestParam String tc, @RequestParam String category, @RequestParam String availability) throws SQLException {

        String ans = null;

        try {
            File img_dest = new File("src/main/resources/static/uploads/images/" + photo.getOriginalFilename());
            File fssai_dest = new File("src/main/resources/static/uploads/fssai_certificates/" + fssai.getOriginalFilename());
            File aadhar_dest = new File("src/main/resources/static/uploads/aadhar_card/" + aadhar.getOriginalFilename());
            File pan_dest = new File("src/main/resources/static/uploads/pancard/" + pan.getOriginalFilename());
            File menu_dest = new File("src/main/resources/static/uploads/menu/" + menu.getOriginalFilename());
            File cheque_dest = new File("src/main/resources/static/uploads/cheque/" + cheque.getOriginalFilename());

            String img_abspath = img_dest.getAbsolutePath();
            String fssai_abspath = fssai_dest.getAbsolutePath();
            String aadhar_abspath = aadhar_dest.getAbsolutePath();
            String pan_abspath = pan_dest.getAbsolutePath();
            String menu_abspath = menu_dest.getAbsolutePath();
            String cheque_abspath = cheque_dest.getAbsolutePath();

            //save mulyipart file as file on disk
            byte img_byte[] = photo.getBytes();
            byte fssai_byte[] = fssai.getBytes();
            byte aadhar_byte[] = aadhar.getBytes();
            byte pan_byte[] = pan.getBytes();
            byte menu_byte[] = menu.getBytes();
            byte cheque_byte[] = cheque.getBytes();

            FileOutputStream fos1 = new FileOutputStream(img_abspath);
            FileOutputStream fos2 = new FileOutputStream(fssai_abspath);
            FileOutputStream fos3 = new FileOutputStream(aadhar_abspath);
            FileOutputStream fos4 = new FileOutputStream(pan_abspath);
            FileOutputStream fos5 = new FileOutputStream(menu_abspath);
            FileOutputStream fos6 = new FileOutputStream(cheque_abspath);

            fos1.write(img_byte);
            fos2.write(fssai_byte);
            fos3.write(aadhar_byte);
            fos4.write(pan_byte);
            fos5.write(menu_byte);
            fos6.write(cheque_byte);

            ans = ans + "<h3>** File Saved on Server ***</h3>";

            ResultSet rs = DBLoader.executeSQL_Query("select * from restaurant_signup where email_id='" + email + "'");

            if (rs.next()) {
                return "Already taken";
            } else {
                rs.moveToInsertRow();
                rs.updateString("restaurant_name", name);
                rs.updateString("city", city);
                rs.updateString("password", password);
                rs.updateString("email_id", email);
                rs.updateString("mobile", mobile);
                rs.updateString("address", address);
                rs.updateString("description", description);
                rs.updateString("photo", "/uploads/images/" + photo.getOriginalFilename());
                rs.updateString("latitude", latitude);
                rs.updateString("longitude", longitude);
                rs.updateString("min_order_delivery", mindelivery);
                rs.updateString("delivery_starts", start);
                rs.updateString("delivery_ends", end);
                rs.updateString("fssai_certificate", "/uploads/fssai_certificates/" + fssai.getOriginalFilename());
                rs.updateString("aadhar_card", "/uploads/aadhar_card/" + aadhar.getOriginalFilename());
                rs.updateString("cancelled_cheque", "/uploads/cheque/" + cheque.getOriginalFilename());
                rs.updateString("pan_card", "/uploads/pancard/" + pan.getOriginalFilename());
                rs.updateString("menu", "/uploads/menu/" + menu.getOriginalFilename());
                rs.updateString("terms_condition", tc);
                rs.updateString("category", category);
                rs.updateString("availability", availability);

                rs.insertRow();
                return "Successfully registered";
            }
        } catch (Exception sqlex) {
            ans = ans + sqlex.toString();
        }

        return ans;

    }

    @PostMapping(value = "/addRider", produces = "text/html")
    String addRider(@RequestParam String email_id, @RequestParam String password, @RequestParam String rider_name,
            @RequestParam String mobile, @RequestParam String address, @RequestParam String city,
            @RequestParam String account_no, @RequestParam MultipartFile identity_proof, @RequestParam MultipartFile rider_licence,
            @RequestParam MultipartFile cancelled_cheque) throws SQLException {

        String ans = null;

        try {
            File licence_dest = new File("src/main/resources/static/uploads/rider_licence/" + rider_licence.getOriginalFilename());
            File id_dest = new File("src/main/resources/static/uploads/rider_id/" + identity_proof.getOriginalFilename());

            File cheque_dest = new File("src/main/resources/static/uploads/rider_cheque/" + cancelled_cheque.getOriginalFilename());

            String licence_abspath = licence_dest.getAbsolutePath();
            String id_abspath = id_dest.getAbsolutePath();
            String cheque_abspath = cheque_dest.getAbsolutePath();

            //save mulyipart file as file on disk
            byte licence_byte[] = rider_licence.getBytes();
            byte id_byte[] = identity_proof.getBytes();

            byte cheque_byte[] = cancelled_cheque.getBytes();

            FileOutputStream fos1 = new FileOutputStream(licence_abspath);
            FileOutputStream fos2 = new FileOutputStream(id_abspath);
            FileOutputStream fos3 = new FileOutputStream(cheque_abspath);

            fos1.write(licence_byte);
            fos2.write(id_byte);
            fos3.write(cheque_byte);

            ans = ans + "<h3>** File Saved on Server ***</h3>";

            ResultSet rs = DBLoader.executeSQL_Query("select * from rider_signup where email_id='" + email_id + "'");

            if (rs.next()) {
                return "Already taken";
            } else {
                rs.moveToInsertRow();
                rs.updateString("rider_name", rider_name);
                rs.updateString("city", city);
                rs.updateString("password", password);
                rs.updateString("email_id", email_id);
                rs.updateString("mobile", mobile);
                rs.updateString("address", address);
                rs.updateString("account_no", account_no);
                rs.updateString("cancelled_cheque", "/uploads/rider_cheque/" + cancelled_cheque.getOriginalFilename());
                rs.updateString("identity_proof", "/uploads/rider_id/" + identity_proof.getOriginalFilename());
                rs.updateString("rider_licence", "/uploads/rider_licence/" + rider_licence.getOriginalFilename());
                rs.insertRow();
                return "Successfully registered";
            }
        } catch (Exception sqlex) {
            ans = ans + sqlex.toString();
        }

        return ans;

    }

    @PostMapping(value = "/three", produces = "application/json")
    String showRestaurant() {
        String ans = RDBMS_TO_JSON.generateJSON("select * from restaurant_signup");
        return ans;
    }

    @PostMapping(value = "/showRestaurantByEmail", produces = "application/json")
    String showRestaurantByEmail(@RequestParam String email) {
        String ans = RDBMS_TO_JSON.generateJSON("select * from restaurant_signup where email_id ='" + email + "'");
        return ans;
    }

    @PostMapping(value = "/getUserData", produces = "application/json")
    String getUserData(@RequestParam String email, @RequestParam String password) {
        String ans = RDBMS_TO_JSON.generateJSON("select * from user where emailid='" + email + "' and password='" + password + "'");
        return ans;
    }

    @PostMapping("/approved")
    public String go2(@RequestParam int id) {
        String s = "";
        ResultSet rs;
        try {
            rs = DBLoader.executeSQL_Query("select * from restaurant_signup where id = '" + id + "'");

            if (rs.next()) {

                rs.moveToCurrentRow();
                rs.updateString("status", "approved");
                rs.updateRow();
                s = "Success";
            } else {
                s = "failed";
            }
//        String ans = new RDBMS_TO_JSON().generateJSON("update doctor set status = 'pending' where id=" + id);
//        return (ans);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;

    }

    @PostMapping("/pending")
    public String go3(@RequestParam int id) {
        String s = "";
        ResultSet rs;
        try {
            rs = DBLoader.executeSQL_Query("select * from restaurant_signup where id = '" + id + "'");

            if (rs.next()) {

                rs.moveToCurrentRow();
                rs.updateString("status", "pending");
                rs.updateRow();
                s = "Success";
            } else {
                s = "failed";
            }
//        String ans = new RDBMS_TO_JSON().generateJSON("update doctor set status = 'pending' where id=" + id);
//        return (ans);
        } catch (Exception ex) {
        }
        return s;

    }

    @PostMapping("/changeStateReady")
    public String changeStateReady(@RequestParam int id) {
        String s = "";
        ResultSet rs;
        try {
            rs = DBLoader.executeSQL_Query("select * from bill_detail where id =" + id);

            if (rs.next()) {

                rs.moveToCurrentRow();
                rs.updateString("status", "ready_to_go");
                rs.updateRow();
                s = "Success";
            } else {
                s = "failed";
            }
        } catch (Exception ex) {
        }
        return s;

    }

    @PostMapping("/changeStatePick")
    public String changeStatePick(@RequestParam int id) {
        String s = "";
        ResultSet rs, rs2;
        try {
            rs = DBLoader.executeSQL_Query("select * from bill_detail where id =" + id);
            if (rs.next()) {

                rs.moveToCurrentRow();
                rs.updateString("status", "order_pick");
                rs.updateRow();
                s = "Success";

            } else {
                s = "failed";
            }

        } catch (Exception ex) {
        }
        return s;

    }

    @PostMapping("/updateinfo")
    public String updateinfo(@RequestParam int id, @RequestParam String mobile, @RequestParam String start, @RequestParam String end, @RequestParam String availability, @RequestParam String category) {
        String s = "";
        ResultSet rs;
        try {
            rs = DBLoader.executeSQL_Query("select * from restaurant_signup where id = '" + id + "'");

            if (rs.next()) {

                rs.moveToCurrentRow();
                rs.updateString("mobile", mobile);
                rs.updateString("category", category);
                rs.updateString("delivery_starts", start);
                rs.updateString("delivery_ends", end);
                rs.updateString("availability", availability);

                rs.updateRow();
                s = "Success";
            } else {
                s = "failed";
            }
//        String ans = new RDBMS_TO_JSON().generateJSON("update doctor set status = 'pending' where id=" + id);
//        return (ans);
        } catch (Exception ex) {
        }
        return s;

    }

    @PostMapping("/updateopenclose")
    public String updateopenclose(@RequestParam int id, @RequestParam String openclose) {
        String s = "";
        ResultSet rs;
        try {
            rs = DBLoader.executeSQL_Query("select * from restaurant_signup where id = '" + id + "'");

            if (rs.next()) {

                rs.moveToCurrentRow();
                rs.updateString("current_status", openclose);

                rs.updateRow();
                s = "Success";
            } else {
                s = "failed";
            }
//        String ans = new RDBMS_TO_JSON().generateJSON("update doctor set status = 'pending' where id=" + id);
//        return (ans);
        } catch (Exception ex) {
        }
        return s;

    }

    @PostMapping("/updatedescription")
    public String updatedescription(@RequestParam int id, @RequestParam String description) {
        String s = "";
        ResultSet rs;
        try {
            rs = DBLoader.executeSQL_Query("select * from restaurant_signup where id = '" + id + "'");

            if (rs.next()) {

                rs.moveToCurrentRow();
                rs.updateString("description", description);
                rs.updateRow();
                s = "Success";
            } else {
                s = "failed";
            }
//        String ans = new RDBMS_TO_JSON().generateJSON("update doctor set status = 'pending' where id=" + id);
//        return (ans);
        } catch (Exception ex) {
        }
        return s;

    }

    @PostMapping(value = "/four", produces = "text/html")
    String sendMail(@RequestParam String email) {
        try {
            ResultSet rs = DBLoader.executeSQL_Query("select * from restaurant_signup where email_id='" + email + "'");

            if (rs.next()) {
                return "Restaurant with this email id already exist.";
            } else {
                StringBuilder html = new StringBuilder();
                String result = "none";
                FileReader fr = new FileReader("C:\\Users\\inder\\OneDrive\\Documents\\NetBeansProjects\\FoodieGo\\src\\main\\resources\\static\\restaurant\\EmailTemplates\\VerifyEmailRestaurant.html");
                try {
                    BufferedReader br = new BufferedReader(fr);
                    String val;
                    while ((val = br.readLine()) != null) {
                        html.append(val);
                    }
                    br.close();
                    result = html.toString();
                } catch (Exception e) {

                }
                sendEmail.sendSimpleEmail(email, result, "Onboarding email verification");

                return "Go to your email and verify";
            }

        } catch (Exception ex) {
            return ex.toString();
        }

    }

    String sendUserOtp(String email) {
        try {
            Random random = new Random();
            String a = String.format("%04d", random.nextInt(9999));
            String result = "Your order is ready to go and your OTP is " + a + "<br>-From Team Foodiego";
            sendEmail.sendSimpleEmail(email, result, "FoodieGo's One Time Password");

            return a;

        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }

    }

    String sendRiderOtp(String email, String order_id, String bid, String item_name, String quantity, String restaurant_name, String restaurant_address) {
        try {
            Random random = new Random();
            String a = String.format("%04d", random.nextInt(9999));
            String result = "Rider Ready!,<br>";
            result += "Pick up order details:<br>";
            result += "Order ID : " + order_id + "<br>";
            result += "Bill_ID: " + bid + "<br>";
            result += "Item Name: " + item_name + "<br>";
            result += "Quantity: " + quantity + "<br>";
            result += "Restaurant Name: " + restaurant_name + "<br>";
            result += "Restaurant Address: " + restaurant_address + "<br>";
            result += "Your OTP(One Time Password) is " + a;
            sendEmail.sendSimpleEmail(email, result, "FoodieGo's One Time Password");

            return a;

        } catch (Exception ex) {
            return ex.toString();
        }

    }

    @PostMapping(value = "/riderEmail", produces = "text/html")
    String riderEmail(@RequestParam String email) {
        try {
            ResultSet rs = DBLoader.executeSQL_Query("select * from rider_signup where email_id='" + email + "'");

            if (rs.next()) {
                return "Rider already register on Foodiego";
            } else {
                StringBuilder html = new StringBuilder();
                String result = "none";
                FileReader fr = new FileReader("C:\\Users\\inder\\OneDrive\\Documents\\NetBeansProjects\\FoodieGoo\\src\\main\\resources\\static\\EmailTemplates\\VerifyEmailRider.html");
                try {
                    BufferedReader br = new BufferedReader(fr);
                    String val;
                    while ((val = br.readLine()) != null) {
                        html.append(val);
                    }
                    br.close();
                    result = html.toString();
                } catch (Exception e) {

                }
                sendEmail.sendSimpleEmail(email, result, "Signup email verification");

                return "Go to your email and verify";
            }

        } catch (Exception ex) {
            return ex.toString();
        }

    }

    void orderDeliveredMail(@RequestParam String email) {
        try {
            StringBuilder html = new StringBuilder();
            String result = "none";
            FileReader fr = new FileReader("C:\\Users\\inder\\OneDrive\\Documents\\NetBeansProjects\\FoodieGoo\\src\\main\\resources\\static\\EmailTemplates\\OrderDeliveredSuccessfully.html");
            try {
                BufferedReader br = new BufferedReader(fr);
                String val;
                while ((val = br.readLine()) != null) {
                    html.append(val);
                }
                br.close();
                result = html.toString();
            } catch (Exception e) {

            }
            sendEmail.sendSimpleEmail(email, result, "Enjoy with Foodiego");
            System.out.println("USER's Email Send Successfully");


        } catch (Exception ex) {
        }

    }

    @PostMapping(value = "/five", produces = "application/json")
    String showRestaurantById(@RequestParam String id) {
        String ans = RDBMS_TO_JSON.generateJSON("select * from restaurant_signup where id=" + id);
        return ans;
    }

    @PostMapping(value = "/showItemById", produces = "application/json")
    String showItemById(@RequestParam String id) {
        String ans = RDBMS_TO_JSON.generateJSON("select * from items where restaurant_id=" + id);
        return ans;
    }

    @PostMapping(value = "/loadRestaurant", produces = "application/json")
    String loadRestaurant(@RequestParam String city) {

        String ans = RDBMS_TO_JSON.generateJSON("select * from restaurant_signup where city='" + city + "' and status='approved' and current_status='Opened' order by rating desc");
        return ans;
    }

    @PostMapping(value = "/loadRestaurant2", produces = "application/json")
    String loadRestaurant2(@RequestParam String city) {

        String ans = RDBMS_TO_JSON.generateJSON("select * from restaurant_signup where city='" + city + "' and status='approved' and current_status='Opened' order by min_order_delivery");
        return ans;
    }

    @PostMapping(value = "/loadRestaurant3", produces = "application/json")
    String loadRestaurant3(@RequestParam String city) {

        String ans = RDBMS_TO_JSON.generateJSON("select * from restaurant_signup where city='" + city + "' and status='approved' and current_status='Opened' order by min_order_delivery desc");
        return ans;
    }

    @PostMapping(value = "/category", produces = "application/json")
    String category() {
        String ans = RDBMS_TO_JSON.generateJSON("select * from cuisines");
        return ans;
    }

    @PostMapping(value = "/addItem", produces = "text/html")
    String addItem(@RequestParam String item_name, @RequestParam String restaurant_id, @RequestParam String subcategory, @RequestParam String item_price, @RequestParam String item_description, @RequestParam String item_category, @RequestParam MultipartFile photo) throws SQLException {

        String ans = null;

        try {
            File img_dest = new File("src/main/resources/static/uploads/item_images/" + photo.getOriginalFilename());
            String img_abspath = img_dest.getAbsolutePath();
            //save mulyipart file as file on disk
            byte img_byte[] = photo.getBytes();
            FileOutputStream fos1 = new FileOutputStream(img_abspath);
            fos1.write(img_byte);
            ans = ans + "<h3>** File Saved on Server ***</h3>";
            ResultSet rs = DBLoader.executeSQL_Query("select * from items where restaurant_id=" + restaurant_id + " and item_name='" + item_name + "'");

            if (rs.next()) {
                return "Item name already exist";
            } else {
                rs.moveToInsertRow();

                rs.updateString("item_description", item_description);
                rs.updateString("item_name", item_name);
                rs.updateString("item_description", item_description);
                rs.updateString("restaurant_id", restaurant_id);
                rs.updateString("item_category", item_category);
                rs.updateString("category", subcategory);
                rs.updateString("price", item_price);
                rs.updateString("item_image", "/uploads/item_images/" + photo.getOriginalFilename());
                rs.insertRow();
                return "Successfully Completed";
            }
        } catch (Exception sqlex) {
            ans = ans + sqlex.toString();
        }

        return ans;

    }

    @PostMapping(value = "/usersignup", produces = "text/html")
    String usersignup(@RequestParam String username, @RequestParam String email, @RequestParam String password, @RequestParam String mobile, @RequestParam String dob) throws SQLException {

        String ans = null;

        try {

            ResultSet rs = DBLoader.executeSQL_Query("select * from user where emailid='" + email + "'");

            if (rs.next()) {
                return "User exist already.";
            } else {

                rs.moveToInsertRow();

                rs.updateString("username", username);
                rs.updateString("password", password);
                rs.updateString("emailid", email);
                rs.updateString("mobileno", mobile);
                rs.updateString("dob", dob);

                rs.insertRow();
                return "success";
            }
        } catch (Exception sqlex) {
            ans = ans + sqlex.toString();
        }

        return ans;

    }

    @PostMapping(value = "/userLogin", produces = "text/html")
    String userLogin(@RequestParam String email, @RequestParam String password, HttpSession session) {
        String result = email + "--> " + password;
        result += "fail";
        try {
            ResultSet rs = DBLoader.executeSQL_Query("select * from user where emailid='" + email + "' and password='" + password + "'");
            if (rs.next()) {
                session.setAttribute("user_id", rs.getInt("id"));
                System.out.println("userId:" + session.getAttribute("user_id"));
                result = "success";
            }
        } catch (Exception e) {
        }
        return result;
    }

    @PostMapping(value = "/riderLogin", produces = "text/html")
    String riderLogin(@RequestParam String email, @RequestParam String password, HttpSession session) {
        String result = email + "--> " + password;
        result += "fail";
        try {
            ResultSet rs = DBLoader.executeSQL_Query("select * from rider_signup where email_id='" + email + "' and password='" + password + "'");
            if (rs.next()) {
                session.setAttribute("user_id", rs.getInt("id"));
                System.out.println("userId:" + session.getAttribute("user_id"));
                result = "success";
            }
        } catch (Exception e) {
        }
        return result;
    }

    @PostMapping(value = "/restaurantLogin", produces = "text/html")
    String restaurantLogin(@RequestParam String email, @RequestParam String password) {
        String result = email + "--> " + password;
        result += "fail";
        try {
            ResultSet rs = DBLoader.executeSQL_Query("select * from restaurant_signup where email_id='" + email + "' and password='" + password + "'");
            if (rs.next()) {
//                session.setAttribute("restaurant_id", rs.getInt("id"));
//                System.out.println(session.getAttribute("restaurant_id"));
                result = "success";
            }
        } catch (Exception e) {
        }
        return result;
    }

    @PostMapping(value = "/ContactUs", produces = "text/html")
    String ContactUs(@RequestParam String name, @RequestParam String email, @RequestParam String subject, @RequestParam String message) throws SQLException {
        String ans = null;
        try {
            ResultSet rs = DBLoader.executeSQL_Query("select * from contactus where email='" + email + "'");
            if (rs.next()) {
                return "Messsage is already send from your email id.";
            } else {
                rs.moveToInsertRow();
                rs.updateString("name", name);
                rs.updateString("email", email);
                rs.updateString("subject", subject);
                rs.updateString("message", message);

                rs.insertRow();
                return "success";
            }
        } catch (Exception sqlex) {
            ans = ans + sqlex.toString();
        }

        return ans;

    }

    @PostMapping(value = "/billing", produces = "text/html")
    String billing(@RequestParam String total_bill_amount, @RequestParam String date, @RequestParam String user_id, @RequestParam String restaurant_id, @RequestParam String delivery_charges, @RequestParam String user_name, @RequestParam String user_mobile, @RequestParam String user_email, @RequestParam String rest_name, @RequestParam String rest_address, @RequestParam String rest_mobile) throws SQLException {
        String ans = null;
        try {

            ResultSet rs = DBLoader.executeSQL_Query("select * from billing");

            rs.moveToInsertRow();
            rs.updateString("total_bill_amount", total_bill_amount);
            rs.updateString("date", date);
            rs.updateString("user_id", user_id);
            rs.updateString("restaurant_id", restaurant_id);
            rs.updateString("delivery", delivery_charges);
            rs.updateString("user_name", user_name);
            rs.updateString("user_mobile", user_mobile);
            rs.updateString("restaurant_name", rest_name);
            rs.updateString("restaurant_address", rest_address);
            rs.updateString("restaurant_mobile", rest_mobile);
            rs.insertRow();
            return "success";

        } catch (Exception sqlex) {
            ans = ans + sqlex.toString();
        }

        return ans;

    }

//    /readyToPick
    @PostMapping(value = "/readyToPick", produces = "text/html")
    String readyToPick(@RequestParam String rid, @RequestParam String rider_name, @RequestParam String rider_mobile, @RequestParam String order_id, @RequestParam String bid, @RequestParam String item_name, @RequestParam String quantity, @RequestParam String rider_email, @RequestParam String restaurant_id) throws SQLException {
        String ans = null;
        try {

            ResultSet rs = DBLoader.executeSQL_Query("select * from bill_detail where id = " + order_id);
            if (rs.next()) {
                rs.moveToCurrentRow();
                rs.updateString("status", "accept_request");
                rs.updateString("rider_name", rider_name);
                rs.updateString("rider_mobile", rider_mobile);
                rs.updateString("rid", rid);
                rs.updateString("rider_otp", sendRiderOtp(rider_email, order_id, bid, item_name, quantity, rs.getString("restaurant_name"), rs.getString("restaurant_address")));
                rs.updateRow();
            }

            return "success";

        } catch (Exception sqlex) {
            ans = ans + sqlex.toString();
            System.out.println(ans);
        }

        return ans;

    }

    @PostMapping(value = "/delivered_successfully", produces = "text/html")
    String Delivered(@RequestParam String order_id) throws SQLException {
        String ans = null;
        try {

            ResultSet rs = DBLoader.executeSQL_Query("select * from bill_detail where id = " + order_id);
            if (rs.next()) {
                rs.moveToCurrentRow();
                rs.updateString("status", "delivered_successfully");
                orderDeliveredMail(rs.getString("user_email"));
                rs.updateRow();
                
            }
            

            return "success";

        } catch (Exception sqlex) {
            ans = ans + sqlex.toString();
            System.out.println(ans);
        }

        return ans;

    }

    @PostMapping(value = "/add", produces = "text/html")
    String add(@RequestParam String bid, @RequestParam String user_id) {
        String ans = "";
        try {

            ResultSet rs2 = DBLoader.executeSQL_Query("SELECT * FROM addtocart where user_id=" + user_id);
            ResultSet rs3 = DBLoader.executeSQL_Query("SELECT * FROM bill_detail");

            while (rs2.next()) {
                ResultSet rs = DBLoader.executeSQL_Query("SELECT * FROM restaurant_signup where id=" + rs2.getString("restaurant_id"));
                if (rs.next()) {
                    rs3.moveToInsertRow();
                    rs3.updateString("item_name", rs2.getString("item_name"));
                    rs3.updateString("user_id", rs2.getString("user_id"));
                    rs3.updateString("item_price", rs2.getString("item_price"));
                    rs3.updateString("quantity", rs2.getString("quantity"));
                    rs3.updateString("restaurant_id", rs2.getString("restaurant_id"));
                    rs3.updateString("item_id", rs2.getString("item_id"));
                    rs3.updateString("subtotal", rs2.getString("subtotal"));
                    rs3.updateString("restaurant_name", rs.getString("restaurant_name"));
                    rs3.updateString("restaurant_address", rs.getString("address"));
                    rs3.updateString("restaurant_mobile", rs.getString("mobile"));
                    rs3.updateString("city", rs.getString("city"));
                    rs3.updateString("bid", bid);
                    double total = (rs2.getDouble("subtotal") * 0.05) + rs2.getDouble("subtotal");
                    System.out.println(total);
                    rs3.updateString("total", total + "");

                }
                ResultSet rs4 = DBLoader.executeSQL_Query("select * from user where id=" + rs2.getString("user_id"));
                if (rs4.next()) {
                    rs3.updateString("username", rs4.getString("username"));
                    rs3.updateString("user_mobile", rs4.getString("mobileno"));
                    rs3.updateString("user_address", rs4.getString("address"));
                    rs3.updateString("user_otp", sendUserOtp(rs4.getString("emailid")));
                    rs3.updateString("user_email", rs4.getString("emailid"));
                }
                rs3.insertRow();
                rs2.deleteRow();

            }

            return "success";

        } catch (Exception sqlex) {
            ans = ans + sqlex.toString();
            System.out.println("ERRROR: " + ans);
        }

        return ans;
    }

    @PostMapping(value = "/addToCart", produces = "text/html")
    String addToCart(@RequestParam String item_name, @RequestParam String restaurant_id, @RequestParam String item_id, @RequestParam String item_price, @RequestParam String user_id) throws SQLException {

        String ans = null;

        try {

            ResultSet rs = DBLoader.executeSQL_Query("select * from addtocart where user_id=" + user_id + " and item_id=" + item_id);
            if (rs.next()) {
                rs.moveToCurrentRow();
                int n = rs.getInt("quantity");
                int quan = n + 1;
                int subtotal = quan * Integer.parseInt(item_price);
                String q = Integer.toString(quan);
                rs.updateString("quantity", q);
                rs.updateString("subtotal", subtotal + "");
                rs.updateRow();
                ans = "success";
                return ans;
            } else {

                rs.moveToInsertRow();

                rs.updateString("item_name", item_name);
                rs.updateString("user_id", user_id);
                rs.updateString("item_price", item_price);
                rs.updateString("restaurant_id", restaurant_id);
                rs.updateString("item_id", item_id);
                rs.updateString("subtotal", item_price);

                rs.insertRow();
                return "success";
            }
        } catch (Exception sqlex) {
            ans = ans + sqlex.toString();
        }

        return ans;

    }

//    deleteFromCart
    @PostMapping(value = "/deleteFromCart", produces = "text/html")
    String deleteFromCart(@RequestParam String item_name, @RequestParam String restaurant_id, @RequestParam String item_id, @RequestParam String item_price, @RequestParam String user_id) throws SQLException {

        String ans = null;

        try {

            ResultSet rs = DBLoader.executeSQL_Query("select * from addtocart where user_id=" + user_id + " and item_id=" + item_id);
            if (rs.next()) {
                int n = rs.getInt("quantity");
                if (n > 1) {
                    rs.moveToCurrentRow();
                    int quan = n - 1;
                    int subtotal = quan * Integer.parseInt(item_price);
                    String q = Integer.toString(quan);
                    rs.updateString("quantity", q);
                    rs.updateString("subtotal", subtotal + "");
                    rs.updateRow();
                    ans = "success";
                    return ans;
                } else {
                    rs.deleteRow();
                    ans = "empty";
                    return ans;
                }
            } else {
                return "Failure!, item doesnot exist in cart.";
            }

        } catch (Exception sqlex) {
            ans = ans + sqlex.toString();
        }

        return ans;

    }

    @PostMapping(value = "/getCartData", produces = "application/json")
    String getCartData(@RequestParam String user_id) {
        String ans = RDBMS_TO_JSON.generateJSON("select * from addtocart where user_id=" + user_id);
        return ans;
    }

    @PostMapping(value = "/getBillDetails", produces = "application/json")
    String getBillDetails(@RequestParam String user_id) {
        String ans = RDBMS_TO_JSON.generateJSON("select * from billing where user_id=" + user_id);
        return ans;
    }

    @PostMapping(value = "/getRiderData", produces = "application/json")
    String getRiderData(@RequestParam String email) {
        String ans = RDBMS_TO_JSON.generateJSON("select * from rider_signup where email_id='" + email + "'");
        return ans;
    }

    @PostMapping(value = "/getBillDetailData", produces = "application/json")
    String getBillDetailData(@RequestParam String city) {
        String ans = RDBMS_TO_JSON.generateJSON("select * from bill_detail where city='" + city + "' and status='ready_to_go'");
        return ans;
    }

    @PostMapping(value = "/myDeliveries", produces = "application/json")
    String myDeliveries(@RequestParam String city, @RequestParam String rid) {
        String ans = RDBMS_TO_JSON.generateJSON("select * from bill_detail where city='" + city + "' and status='order_pick' and rid=" + rid);
        return ans;
    }

    @PostMapping(value = "/getDeliveryData", produces = "application/json")
    String getDeliveryData(@RequestParam String id) {
        String ans = RDBMS_TO_JSON.generateJSON("select * from bill_detail where restaurant_id=" + id + " and status='accept_request'");
        System.out.println("Ans" + ans);
        return ans;
    }

    @PostMapping(value = "/orderList", produces = "application/json")
    String orderList(@RequestParam String rest_id) {
        String ans = RDBMS_TO_JSON.generateJSON("select * from bill_detail where restaurant_id=" + rest_id + " and status='pending'");
        return ans;
    }

    @PostMapping("/addAddress")
    public String addAddress(@RequestParam int id, @RequestParam String address) {
        String s = "";
        ResultSet rs, rs2;
        try {
            rs = DBLoader.executeSQL_Query("select * from user where id =" + id);
            rs2 = DBLoader.executeSQL_Query("select * from billing where user_id =" + id);

            if (rs.next()) {

                rs.moveToCurrentRow();
                rs.updateString("address", address);
                rs.updateRow();
                s = "Success";
            } else {
                s = "failed";
            }

            if (rs2.next()) {

                rs2.moveToCurrentRow();
                rs2.updateString("user_address", address);
                rs2.updateRow();
                s = "Success";
            } else {
                s = "failed";
            }
//        String ans = new RDBMS_TO_JSON().generateJSON("update doctor set status = 'pending' where id=" + id);
//        return (ans);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;

    }

}
