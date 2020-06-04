import { Router } from "express";
import UserRouter from "./Users";
import PathRouter from "./Paths";
import UserInfoRouter from './UserInfos';
import RecommendRouter from './Recommend';
import PlaceRouter from './Place';

// Init router and path
const router = Router();

// Add sub-routes
router.use("/member", UserRouter);
router.use("/path", PathRouter);
router.use("/userinfo",UserInfoRouter);
router.use("/recommend",RecommendRouter);
router.use("/place/",PlaceRouter);

// Export the base-router
export default router;
