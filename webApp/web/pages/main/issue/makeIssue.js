function HandelingResponse(response){
if(response=="success\r\n"){
        window.alert("Issuance successful");
        clearForm();
        }
        else if(response=="symbolalreadyexist_error\r\n"){
        window.alert("Symbol already exist");
        }
        else if(response=="companynamealreadyexist_error\r\n"){
        window.alert("Company name already exist");
        }
        else if(response=="parameters_error\r\n"){
         window.alert("Please Fill in all the fields");
        }
}



function clearForm(){
document.getElementById("makeissueform").reset();
}
