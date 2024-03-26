var profileName = null;
var customername = null;
var myLink = document.getElementById("featureShipNow");
var myLinkforTracking = document.getElementById("featureTrack");
//document.getElementById("headerPartForDeliveryBoy").style.display = "none";
function addHref() {
  myLink.setAttribute('href', "/MainProject_Courier/DeliveryDetails.html");
}

function addHrefForTracking() {
  myLinkforTracking.setAttribute('href', "/MainProject_Courier/Tracking.html");
}

function mainpage() {
  document.getElementById("successfulPayment").style.display = "none";
  goMainpage();
}

function goMainpage() {
  window.location.href = ("/MainProject_Courier/mainPage.html");
  if (getCookie('username') != null) {
    nameChange(username[1]);
  } else {
    alert("Err");
  }
}

function deliveryPartnerLogin(){
	var username = document.getElementById("nameForDeliveryBoyLogin").value;
  var password = document.getElementById("passwordForDeliveryBoyLogin").value;
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (this.readyState == 4) {
      if (this.status == 200) {
        var str = this.responseText;
        if (str.trim() !== "") {
          var loginResponse = JSON.parse(str);
          if (loginResponse.statusCode == 200) {
            nameValue = document.cookie;
            console.log(document.cookie);
            setTimeout(() => {
              goMainpageForDeliveryBoy();
            });
          } else {
            document.getElementById("nameForDeliveryBoyLogin").value = "";
            document.getElementById("passwordForDeliveryBoyLogin").value = "";
            alert(loginResponse.error);
          }
        } else {
          console.error("Empty or invalid JSON response");
        }
      } else {
        console.error("HTTP request failed with status: " + this.status);
      }
    }
  };
  var jsonData = {
    "username": username,
    "password": password
  };
  xhr.open("POST", "http://localhost:9000/MainProject_Courier/deliveryBoyLogin", true);
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.send(JSON.stringify(jsonData));
  console.log(JSON.stringify(jsonData));
}



function goMainpageForDeliveryBoy() {
	console.log("Checking delivery boy");
  window.location.href = ("/MainProject_Courier/mainPage.html");
	
}

var usernameForProfile;
window.onload = (e) => {

  if (document.cookie !== "") {
	   let username =  document.cookie.split(/[=;:]/).map(x => x.trim())
	  if(username[5]=="Customer"){
    document.getElementById("featureRegister").style.display = "none";
    console.log("tesingForRegister");
    addHref();
    addHrefForTracking();
    document.getElementById("userNametoShowText").innerText = username[3];
    usernameForProfile = username[3];
    console.log(username);
     document.getElementById("featurePartner").style.display = "none";
    document.getElementById("featureLogin").style.display = "block";
    
    
    document.getElementById("featureLogin").style.display = "flex";
    document.getElementById("userNametoShowText").style.color = "white";
    }
    else if(username[5]=="DeliveryBoy"){
	document.getElementById("headerPart").style.display = "none";
	document.getElementById("headerPartForDeliveryBoy").style.display = "block";
	document.getElementById("headerPartForDeliveryBoy").style.display = "flex";
	}
  }
}

function nameChange(name) {
  document.getElementById("registerName").innerHTML = name;
}

function goSigninpage() {
  window.location.href = ("/MainProject_Courier/register.html");
}


function getOrderlistMiniDetails(partnerId, statusType) {
  var xhr = new XMLHttpRequest();
  var jsonData = {
    "partnerId": partnerId,
    "statusType": statusType
  };

  xhr.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      var str = this.responseText;
      var jsonResultforMinidetails = JSON.parse(str);
      console.log("Response from server:", jsonResultforMinidetails);
      createDivsWithParagraphs(jsonResultforMinidetails); 
    }
  }

  xhr.open("POST", "http://localhost:9000/MainProject_Courier/orderlistMiniDetails", true);
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.send(JSON.stringify(jsonData));
}

function orderListShow() {
  var splitname =  document.cookie.split(/[=;:]/).map(x => x.trim());
  var partnerId = splitname[7];
  var orderListTemp = document.getElementById("OrderListFilter");
  var orderListOption = orderListTemp.value;
  console.log("Fetching order list...");
  getOrderlistMiniDetails(partnerId, orderListOption);
}

function createDivsWithParagraphs(jsonData) {
  var section = document.getElementById("sectionForOrderList");
  if (!Array.isArray(jsonData)) {
    console.error("jsonData is not an array.");
    return;
  }
  var count = 0;
  jsonData.forEach(function(x) {
    count++;
    var div = document.createElement("div");
    div.className = "orderItem";
    div.id = "" + x.Pack_Id;

    let receivername = document.createElement("p");
    receivername.className = "commonsizeForOrderlist commonsizeForOrderlist1 ";
    receivername.id = "receivername";
    let textNode2 = document.createTextNode("Receiver: " + x.Receiver_Name);
    receivername.appendChild(textNode2);
    div.appendChild(receivername);

    let status = document.createElement("p");
    status.className = "commonsizeForOrderlist commonsizeForOrderlist2";
    status.id = "status";
    let textNode = document.createTextNode("Status: " + x.Delivery_Status);
    status.appendChild(textNode);
    div.appendChild(status);

    let weight = document.createElement("p");
    weight.className = "commonsizeForOrderlist commonsizeForOrderlist3";
    weight.id = "weight";
    let textNode3 = document.createTextNode("Weight: " + x.Weight + "g");
    weight.appendChild(textNode3);
    div.appendChild(weight);

    let date = document.createElement("p");
    date.className = "commonsizeForOrderlist commonsizeForOrderlist4";
    date.id = "date";
    let textNode4 = document.createTextNode("Delivery date: " + x.Delivery_Date);
    date.appendChild(textNode4);
    div.appendChild(date);

    let amount = document.createElement("p");
    amount.className = "commonsizeForOrderlist commonsizeForOrderlist5";
    amount.id = "amount";
    let textNode5 = document.createTextNode("Amount: " + "Rs. " + x.Amount);
    amount.appendChild(textNode5);
    div.appendChild(amount);

    section.appendChild(div);
  });
  
  var divs = document.querySelectorAll('.orderItem');


divs.forEach(function(div) {
  div.addEventListener('click', function() {
     var packid = this.id;
    console.log('Clicked div ID:', packid);
    getOrderlistLargeDetails(packid);
  });
});
}

function getOrderlistLargeDetails(packID) {
  var xhr = new XMLHttpRequest();

  xhr.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      var str = this.responseText;
      var jsonResultforLargedetails = JSON.parse(str);
      console.log("Response from server:", jsonResultforLargedetails);
   
    }
  }

  xhr.open("POST", "http://localhost:9000/MainProject_Courier/orderlistLargeDetails", true);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
   xhr.send("packId=" + packID);
}

function hideTrackBox() {
  var trackIDNumber = document.getElementById("inputBoxForTrackID").value;
  if (trackIDNumber != null) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {
        console.log("CheckforTracking");
        var str = this.responseText;
        var trackingResponse = JSON.parse(str);
        console.log(trackingResponse);

        if (trackingResponse.statusCode == 200) {
          document.getElementById("boxForTrackID").style.display = "none";
          document.getElementById("boxforTrackDetails").style.display = "block";
          document.getElementById("boxforTrackDetails").style.display = "flex";
          document.getElementById("receiverNameforTracking").innerText = trackingResponse.Name;
          document.getElementById("receiverEmailIDforTracking").innerText = trackingResponse.EmailId;
          document.getElementById("receiverMobilenumberforTracking").innerText = trackingResponse.Phone_number;
          document.getElementById("receiverAddressforTracking").innerText = trackingResponse.Address;
          document.getElementById("packageTrackingcodeforTracking").innerText = trackingResponse.Tracking_code;
          document.getElementById("packageItemnameforTracking").innerText = trackingResponse.Item;
          document.getElementById("packageWeightforTracking").innerText = trackingResponse.Weight;
          document.getElementById("packageAmountforTracking").innerText = trackingResponse.Amount;
          document.getElementById("packageDescriptionforTracking").innerText = trackingResponse.Description;
          document.getElementById("packageTypeofDeliveryforTracking").innerText = trackingResponse.Type_of_delivery;
          document.getElementById("packageTypeofPaymentforTracking").innerText = trackingResponse.Type_of_payment;
          document.getElementById("packageDeliveryStatusforTracking").innerText = trackingResponse.Delivery_status;
          document.getElementById("packageOrdereddateforTracking").innerText = trackingResponse.Ordered_date;
          document.getElementById("packageDeliverydateforTracking").innerText = trackingResponse.Delivery_date;
        } else {
          alert(trackingResponse.error);
        }
      }
    }
    xhr.open("POST", "http://localhost:9000/MainProject_Courier/TrackCourierServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("trackID=" + trackIDNumber);
  }
}

function closeProfile() {
  document.getElementById("profilePage").style.display = "none";
}
var receiverID;

function receiverDetails() {
  var receiverName = document.getElementById("receiverName").value;
  var receiverEmailID = document.getElementById("receiverEmailID").value;
  var receiverMobileNumber = document.getElementById("receiverMobileNumber").value;
  var receiverAddress = document.getElementById("receiverAddress").value;
  console.log("topackagecheck" + receiverName);
  if ((receiverName != null) && (receiverEmailID != null) && (receiverMobileNumber != null) && (receiverAddress != null)) {

    var jsonData = {
      "receiverName": receiverName,
      "receiverEmailID": receiverEmailID,
      "receiverMobileNumber": receiverMobileNumber,
      "receiverAddress": receiverAddress
    };
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {

        var str = this.responseText;
        var receiverResponse = JSON.parse(str);

        if (receiverResponse.statusCode == 200) {
          receiverID = receiverResponse.receiverID;
          document.getElementById("receiverDetailsBox").style.display = "none";
          document.getElementById("PackageDetails").style.display = "block";
          document.getElementById("PackageDetails").style.display = "flex";

          alert("Receiver details stored Successfully!!");
        } else {
          alert(receiverResponse.error);
        }
      }
    }
    xhr.open("POST", "http://localhost:9000/MainProject_Courier/ReceiverServlet", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(jsonData));
    console.log(JSON.stringify(jsonData));
  }
}
var trackingcode = 0;

function packageDetails() {
  var itemName = document.getElementById("itemName").value;
  console.log(itemName);
  var weight = document.getElementById("weight").value;
  var description = document.getElementById("description").value;
  var typeOfDeliveryTemp = document.getElementById("typeOfDelivery");
  var paymentTypeTemp = document.getElementById("paymentType");
  var typeOfDelivery = typeOfDeliveryTemp.options[typeOfDeliveryTemp.selectedIndex].value;
  var paymentType = paymentTypeTemp.options[paymentTypeTemp.selectedIndex].value;
  typeOfDelivery = typeOfDelivery.toUpperCase();
  paymentType = paymentType.toUpperCase();
  if (document.cookie !== "") {
      let username =  document.cookie.split(/[=;:]/).map(x => x.trim())
    customername = username[3];
  }
  console.log(typeOfDelivery);
  console.log(paymentType);
  var amount = 0;
  var jsonData;
  var paymenttype = null;
  if ((itemName != null) && (weight != null) && (description != null) && (typeOfDelivery != null) && (paymentType != null)) {

    jsonData = {
      "itemName": itemName,
      "weight": weight,
      "description": description,
      "typeOfDelivery": typeOfDelivery,
      "paymentType": paymentType,
      "receiverID": receiverID,
      "customerName": customername
    };
    console.log(JSON.stringify(jsonData));
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {

        var str = this.responseText;
        var packageResponse = JSON.parse(str);

        if (packageResponse.statusCode == 200) {
          amount = packageResponse.amount;
          trackingcode = packageResponse.trackingCode;
          console.log("trackingcode: " + trackingcode);
          paymenttype = packageResponse.paymentType;
          if (paymenttype == "CASHONDELIVERY") {

            document.getElementById("PackageDetails").style.display = "none";
            document.getElementById("successfulPayment").style.display = "block";
            document.getElementById("successfulPayment").style.display = "flex";
            document.getElementById("trackingCode").innerText = trackingcode;
            console.log("trackingcode2: " + trackingcode);
          } else {
            document.getElementById("PackageDetails").style.display = "none";
            document.getElementById("paymentDetails").style.display = "block";
            document.getElementById("paymentDetails").style.display = "flex";
            document.getElementById("money").innerText = amount;
          }

          alert("Package details stored Successfully!!");
        } else {
          alert(packageResponse.error);
        }
      }

    }

    xhr.open("POST", "http://localhost:9000/MainProject_Courier/PackageDetailsServlet", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(jsonData));
    console.log("Hii");
    console.log(JSON.stringify(jsonData));
  }
}

function toSuccessful() {
  paymentDetails
  document.getElementById("paymentDetails").style.display = "none";
  document.getElementById("successfulPayment").style.display = "block";
  document.getElementById("successfulPayment").style.display = "flex";
  document.getElementById("trackingCode").innerText = trackingcode;
}

var nameValue = null;

function loginUser() {
  var username = document.getElementById("nameForLogin").value;
  var password = document.getElementById("passwordForLogin").value;
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (this.readyState == 4) {
      if (this.status == 200) {
        var str = this.responseText;
        if (str.trim() !== "") {
          var loginResponse = JSON.parse(str);
          if (loginResponse.statusCode == 200) {
            nameValue = document.cookie;
            console.log(document.cookie);
            setTimeout(() => {
              goMainpage();
            });
          } else {
            document.getElementById("nameForLogin").value = "";
            document.getElementById("passwordForLogin").value = "";
            alert(loginResponse.error);
          }
        } else {
          console.error("Empty or invalid JSON response");
        }
      } else {
        console.error("HTTP request failed with status: " + this.status);
      }
    }
  };
  var jsonData = {
    "username": username,
    "password": password
  };
  xhr.open("POST", "http://localhost:9000/MainProject_Courier/login", true);
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.send(JSON.stringify(jsonData));
  console.log(JSON.stringify(jsonData));
}




function registerUser() {
  var username = document.getElementById("username").value;
  var password = document.getElementById("password").value;
  var emailID = document.getElementById("emailID").value;
  var address = document.getElementById("address").value;
  var mobileNumber = document.getElementById("mobileNumber").value;
  var xhr = new XMLHttpRequest();
  var jsonData = {
    "username": username,
    "password": password,
    "emailID": emailID,
    "address": address,
    "mobileNumber": mobileNumber
  };

  xhr.open("POST", "http://localhost:9000/MainProject_Courier/Register", true);
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.send(JSON.stringify(jsonData));
  console.log(JSON.stringify(jsonData));

  if (isStrongPassword(password)) {
    xhr.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {

        var str = this.responseText;
        var registerResponse = JSON.parse(str);

        if (registerResponse.statusCode == 200) {
          goSigninpage();
          alert("Registered Successfully!!");
        } else {
          alert(registerResponse.error);
        }
      }
    }

  } else {
    document.getElementById("username").value = "";
    document.getElementById("mobileNumber").value = "";
    document.getElementById("emailID").value = "";
    document.getElementById("address").value = "";
    document.getElementById("password").value = "";
    alert("Your password is weak!!Please enter valid password");
  }
}

function showProfile() {
  document.getElementById("profilePage").style.display = "block";
  document.getElementById("profilePage").style.display = "flex";

  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      try {
        var str = this.responseText;
        var profileResponse = JSON.parse(str);

        document.getElementById("profileNameForBox").innerText = profileResponse.name;
        document.getElementById("profileID").innerText = profileResponse.userID;
        document.getElementById("profileEmailID").innerText = profileResponse.email_ID;
        document.getElementById("profileAddress").innerText = profileResponse.address;
        document.getElementById("profileNumber").innerText = profileResponse.mobile_Number;
        document.getElementById("profileOrderCount").innerText = profileResponse.orderCount;

        console.log(profileResponse);
      } catch (error) {
        console.error("Error parsing server response: " + error);
      }
    }
  }

  xhr.open("POST", "http://localhost:9000/MainProject_Courier/ShowProfile", true);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.send("username=" + usernameForProfile);
}

function logoutOption() {
  var xhr = new XMLHttpRequest();

  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4 && xhr.status == 200) {
      console.log(xhr.responseText);

    }
  };
  xhr.open("POST", "http://localhost:9000/MainProject_Courier/logout", true);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.send();
}

function isStrongPassword(password) {
  // Check for null
  if (password == null) {
    return false;
  }

  // Minimum 8 characters
  const minLengthCondition = password.length >= 8;

  // At least 1 uppercase letter
  const uppercaseCondition = /[A-Z]/.test(password);

  // At least 1 number
  const numberCondition = /\d/.test(password);

  return minLengthCondition && uppercaseCondition && numberCondition;
}

const signUpButton = document.getElementById("signUp");
const signInButton = document.getElementById("signIn");
const container = document.getElementById("container");
signUpButton.addEventListener('click', () => {
  container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
  container.classList.remove("right-panel-active");
});