let d = (sx, sy, ex, ey) => {
	
	let dx = sx - ex
	let dy = sy - ey
	
	let ans = Math.sqrt(dx*dx + dy*dy).toFixed(1)
	console.log("DISTANCE:", ans)
}
