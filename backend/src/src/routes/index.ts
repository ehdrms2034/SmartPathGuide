import { Router } from "express";
import UserRouter from "./Users";
import PathRouter from "./Paths";
import UserInfoRouter from './UserInfos';

// Init router and path
const router = Router();

// Add sub-routes
router.use("/user", UserRouter);
router.use("/path", PathRouter);
router.use("/userInfo",UserInfoRouter);

// Export the base-router
export default router;
