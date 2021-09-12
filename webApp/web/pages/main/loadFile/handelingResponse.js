function handelingResponse(){
   // let's start the jQuery while I wait.
            // step 1: onload - capture the submit event on the form.
            $(function() { // onload...do
                $("#uploadForm").submit(function() {

                    var file1 = this[0].files[0];


                    var formData = new FormData();
                    formData.append("fake-key-1", file1);


                      $.ajax({
                        method:'POST',
                        data: formData,
                         dataType: 'json',
                        url: this.action,
                        processData: false, // Don't process the files
                        contentType: false, // Set content type to false as jQuery will tell the server its a query string request
                       // timeout: 4000,
                          success: function(e) {
                         var msg= e.massage;
                          $("#result").text(msg);
                         },
                          error: function() {
                          $("#result").text("Faild to upload the file");
                        },

                    });

                    // return value of the submit operation
                    // by default - we'll always return false so it doesn't redirect the user.
                    return false;
                })
            })}