import { Router } from "express";
import UserRouter from "./Users";
import PathRouter from "./Paths";

// Init router and path
const router = Router();

// Add sub-routes
router.use("/users", UserRouter);
router.use("/path", PathRouter);

// Export the base-router
export default router;
