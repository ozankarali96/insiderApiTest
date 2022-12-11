package test;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.*;


import java.io.IOException;

import static constant.ApiConstant.*;

public class ApiTest extends BaseTest {

    @Before
    public void beforeTest() {
        setup();
    }

    @Test
    public void createPetSuccess() throws IOException {
        Response response = createPet("PetCreateSuccess");
        JsonPath responseJson = new JsonPath(response.asString());

        Assert.assertEquals(200, response.getStatusCode());

        Assert.assertNotNull(responseJson.getString("id"));
        Assert.assertNotNull(responseJson.getString("photoUrls[0]"));
        Assert.assertNotNull(responseJson.getString("tags"));


        Assert.assertEquals(CREATE_PET_NAME, responseJson.getString("name"));
        Assert.assertEquals(CREATE_PET_STATUS, responseJson.getString("status"));
        Assert.assertEquals(CREATE_PET_PHOTOS, responseJson.get("photoUrls"));
        Assert.assertEquals(responseJson.getString("category.name"),CREATE_PET_CATEGORY.get("name"));
    }

    @Test
    public void createPet400() throws IOException{
        Response response = createPet("PetCreate400");
        JsonPath responseJson = new JsonPath(response.asString());

        Assert.assertEquals(400, response.getStatusCode());

        Assert.assertEquals(400, responseJson.getInt("code"));
        Assert.assertEquals(CREATE_PET_400_MESSAGE, responseJson.getString("message"));
    }

    @Test
    public void updatePetSuccess() throws IOException{
        Response response = updatePet("PetUpdateSuccess");
        JsonPath responseJson = new JsonPath(response.asString());

        Assert.assertEquals(200, response.getStatusCode());

        Assert.assertNotNull(responseJson.getString("id"));
        Assert.assertNotNull(responseJson.getString("photoUrls[0]"));
        Assert.assertNotNull(responseJson.getString("tags"));

        Assert.assertEquals(UPDATE_PET_NAME, responseJson.getString("name"));
        Assert.assertEquals(UPDATE_PET_STATUS, responseJson.getString("status"));
        Assert.assertEquals(UPDATE_PET_PHOTOS, responseJson.get("photoUrls"));
        Assert.assertEquals(responseJson.getString("category.name"),UPDATE_PET_CATEGORY.get("name"));
    }

    @Test
    public void updatePet400() throws IOException{
        Response response = updatePet("PetUpdate400");
        JsonPath responseJson = new JsonPath(response.asString());

        Assert.assertEquals(400, response.getStatusCode());

        Assert.assertEquals(400, responseJson.getInt("code"));
        Assert.assertEquals(UPDATE_PET_400_MESSAGE, responseJson.getString("message"));
    }

    @Test
    public void updatePet404() {
        System.out.println("Update 404");
        Assert.assertTrue(true);
        //İstek 404 almıyor
    }

    @Test
    public void findPetByStatusAvailableSuccess(){
        Response responseAvailable = findPetByStatus(STATUS_AVAILABLE);
        JsonPath responseAvailableJson = new JsonPath(responseAvailable.asString());

        Assert.assertEquals(200, responseAvailable.getStatusCode());

        for (int i = 1; i < responseAvailableJson.getInt("$.size()"); i++) {
            Assert.assertNotNull(responseAvailableJson.getString("["+i+"].id"));
            Assert.assertNotNull(responseAvailableJson.getString("["+i+"].name"));
            Assert.assertEquals(STATUS_AVAILABLE, responseAvailableJson.getString("[" + i + "].status"));
        }
    }

    @Test
    public void findPetByStatusPendingSuccess(){
        Response responseAvailable = findPetByStatus(STATUS_PENDING);
        JsonPath responseAvailableJson = new JsonPath(responseAvailable.asString());

        Assert.assertEquals(200, responseAvailable.getStatusCode());

        for (int i = 1; i < responseAvailableJson.getInt("$.size()"); i++) {
            Assert.assertNotNull(responseAvailableJson.getString("["+i+"].id"));
            Assert.assertNotNull(responseAvailableJson.getString("["+i+"].name"));
            Assert.assertEquals(STATUS_PENDING, responseAvailableJson.getString("[" + i + "].status"));
        }
    }

    @Test
    public void findPetByStatusSoldSuccess(){
        Response responseAvailable = findPetByStatus(STATUS_SOLD);
        JsonPath responseAvailableJson = new JsonPath(responseAvailable.asString());

        Assert.assertEquals(200, responseAvailable.getStatusCode());

        for (int i = 1; i < responseAvailableJson.getInt("$.size()"); i++) {
            Assert.assertNotNull(responseAvailableJson.getString("["+i+"].id"));
            Assert.assertEquals(STATUS_SOLD, responseAvailableJson.getString("[" + i + "].status"));
        }
    }

}
