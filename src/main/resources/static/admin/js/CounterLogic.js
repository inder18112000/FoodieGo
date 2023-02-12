/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


function load(no_of_restaurant,total_riders,active_riders,today_orders,pending_orders,cancelled_order)
{
    numberWang =
            {
                countUpOrDown: function (containerid, startingNumber, endingNumber, betweenNumberDuration, betweenEffect, effectDuration, endingEffect, endingEffectDuration)
                {
                    // Required parameters
                    var divPlaceholder = document.getElementById(containerid);
                    var fixedStartingNumber = parseInt(startingNumber.toFixed(0)) + 1;
                    var fixedEndingNumber = parseInt(endingNumber.toFixed(0)) + 1;

                    // Optional parameters
                    betweenNumberDuration = betweenNumberDuration || 20;
                    betweenEffect = betweenEffect || "jello";
                    effectDuration = effectDuration || "0.1s";
                    endingEffect = endingEffect || "tada";
                    endingEffectDuration = endingEffectDuration || "1s";


                    // if both numbers ARE numbers
                    if (isNaN(startingNumber, endingNumber))
                    {
                        console.log('both starting and ending numbers are not integars!');
                        return false;
                    } else
                    {
                        if (fixedStartingNumber < 0)
                        {
                            divPlaceholder.innerHTML = fixedStartingNumber - 1;
                        } else
                        {
                            divPlaceholder.innerHTML = fixedStartingNumber;
                        }

                        if (fixedEndingNumber < 0)
                        {
                            var fixedEndingNumber = parseInt(endingNumber.toFixed(0)) - 1;
                        } else
                        {
                            var fixedEndingNumber = parseInt(endingNumber.toFixed(0)) + 1;
                        }

                        if (fixedStartingNumber > fixedEndingNumber)
                        {
                            function updateToEndNumber()
                            {
                                divPlaceholder.style.animationDuration = effectDuration;
                                divPlaceholder.setAttribute("class", betweenEffect + " animated infinite");
                                divPlaceholder.innerHTML = fixedStartingNumber--;
                                if (fixedStartingNumber == fixedEndingNumber) {
                                    clearInterval(clearCountTimer);
                                    clearCountTimer = 0;
                                    divPlaceholder.style.animationDuration = endingEffectDuration;
                                    divPlaceholder.setAttribute("class", endingEffect + " animated");
                                }
                            }
                            var clearCountTimer = setInterval(updateToEndNumber, betweenNumberDuration);
                        } else if (fixedStartingNumber < fixedEndingNumber)
                        {
                            function updateToEndNumber()
                            {
                                divPlaceholder.style.animationDuration = effectDuration;
                                divPlaceholder.setAttribute("class", betweenEffect + " animated infinite");
                                divPlaceholder.innerHTML = fixedStartingNumber++;
                                if (fixedStartingNumber === fixedEndingNumber)
                                {
                                    clearInterval(clearCountTimer);
                                    clearCountTimer = 0;
                                    divPlaceholder.style.animationDuration = endingEffectDuration;
                                    divPlaceholder.setAttribute("class", "" + endingEffect + " animated");
                                }
                            }
                            var clearCountTimer = setInterval(updateToEndNumber, betweenNumberDuration);
                        } else if (fixedStartingNumber === fixedEndingNumber)
                        {
                            clearInterval(clearCountTimer);
                            clearCountTimer = 0;
                            divPlaceholder.style.animationDuration = endingEffectDuration;
                            divPlaceholder.setAttribute("class", "" + endingEffect + " animated");
                        }
                    }
                }


            };
  

    numberWang.countUpOrDown('no_of_restaurant', 0, no_of_restaurant, 30, "shake", "0.01s", "tada", "2s");
    numberWang.countUpOrDown('total_riders', 0, total_riders, 30, "shake", "0.01s", "tada", "2s");
    numberWang.countUpOrDown('active_riders', 0, active_riders, 30, "shake", "0.01s", "tada", "2s");
    numberWang.countUpOrDown('today_orders', 0, today_orders, 30, "shake", "0.001s", "tada", "2s");
    numberWang.countUpOrDown('pending_order', 0, pending_orders, 30, "shake", "0.001s", "tada", "2s");
    
    numberWang.countUpOrDown('cancelled_order', 0, cancelled_order, 30, "shake", "0.001s", "tada", "1s");
    

}