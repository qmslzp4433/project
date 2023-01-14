

	var lagAmount = 50;
	var maxSpeed = 100;
	var frameRate = 20;
	var selector = '.wrapper';
	//code
	var scrollTop = 0;
	var pinTop = 0;
	var lastTime = void 0;
	var updatePinPosition = function updatePinPosition(time) {
	  if (!lastTime) lastTime = time;
	  var delta = time - lastTime;
	  if (delta >= frameRate) {
	    scrollTop = $(window).scrollTop();
	    var move = (scrollTop - pinTop) * delta / (lagAmount + delta);
	    var direction = move === 0 ? 0 : move / Math.abs(move);
	    pinTop = pinTop + Math.min(Math.abs(move), maxSpeed) * direction;
	    $(selector).css('transform', 'translateY(' + -move + 'px');
	    lastTime = time;
	  }
	  requestAnimationFrame(updatePinPosition);
	};
	requestAnimationFrame(updatePinPosition);