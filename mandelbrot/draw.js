window.onload = 
	function() {
		var canvas = document.createElement("canvas");
		var ctx = canvas.getContext("2d");
		ctx.canvas.width = window.innerWidth;
		ctx.canvas.height = window.innerHeight;

		var bounded = 16;
		var maxIter = 100;

		for(var i = 0; i < window.innerWidth; i++) {
			for(var j = 0; j < window.innerHeight; j++) {
				var px = (i / window.innerWidth) * 3.5 - 2.5;
				var py = (j / window.innerHeight) * 2 - 1;
				var curIter = 0;
				/*
				tn = an + bni
				tn = (tn-1)^2 + (a0 + b0 * i)
				tn = (a^2 - b^2 + 2abi) + (a0 + b0 * i)
				tn = (a^2 - b^2 + a0) + (2ab + b0) * i
				*/
				var a1 = px;
				var b1 = py;
				var flag = true;
				while(flag && curIter < maxIter) {
					var a2 = a1*a1 - b1*b1 + px;
					var b2 = 2*a1*b1 + py;
					if(a2*a2 > bounded) {
						flag = false;
					} else {
						a1 = a2;
						b1 = b2;
						curIter++;
					}
				}
				var rgb = parseInt((((100 - curIter) / 100) * 254).toString());
				ctx.fillStyle = "rgb(" + rgb + ", " + rgb + "," + rgb + ")";
				ctx.fillRect(i, j, 1, 1);
			}
		}
	
		document.body.appendChild(canvas);
		document.getElementById("mandelbrot").appendChild(canvas);
		document.getElementById("loader").style.display = "none";
	}