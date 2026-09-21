$input v_color0，v_fog，v_light
#include<bgfx_shader.sh>
#include<MinecraftRenderer.材料/演员Util.Dragonh>
#include<newb/main.sh>
均匀的vec4ChangeColor；
均匀的vec4叠加颜色；
基于颜色的统一vec4；
均匀vec4MatColor；
统一vec4乘法tintColor；
空干管(){
#if已定义(DEPTH_ONLY)||已定义(实例)
GL_FragColor=vec4(0.0，0.0，0.0，0.0)；
返回；
#endif
vec4反照率=vec4(mix(vec3(1.0，1.0，1.0)，v_color0.RGB，ColorBased.X)，1。0)；
#ifdef MULTI_COLOR_TINT
反照率=applyMultiColorChange(反照率，ChangeColor.rgb、MultiplicativeTintColor.rgb)；
#else
反照率=应用颜色变化(反照率，变化颜色，反照率.一个)；
反照率.a*=ChangeColor.a；
#endif
反照率=应用OverlayColor(反照率，OverlayColor)；
#ifdef ALPHA_TEST
if(反照率a<0.5){
丢弃；
    }
#endif
//发光判定掩模
float diff=v_color0.a-0.99；
浮动掩码=1.0-平滑步(-0.0001，0.0001，diff)；
vec3baseRaw=反照率.RGB；
//管线A：发光像素，完全不参与环境光照，固定亮度
vec3emisitePath=baseRaw*1.2；
//管线B：普通像素，完整标准光照流程
vec3litPath=baseRaw；
litPath*=litPath*v_light.rgb；
litPath=mix(litPath，v_fog.rgb，v_fog.a)；
litPath=颜色校正(litPath)；
//二选一输出，无if，编译安全
albedo.rgb=混合(litPath，emissivePath，mask)；
GL_FragColor=反照率；
}
